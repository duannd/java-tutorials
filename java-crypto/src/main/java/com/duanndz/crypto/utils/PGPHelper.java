package com.duanndz.crypto.utils;

import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;

/**
 * Created By duan.nguyen at 4/20/20 6:26 PM
 */
public final class PGPHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PGPHelper.class);
    private static PGPHelper INSTANCE;
    private static final int BUFFER_SIZE = 1 << 16; // should always be power of 2 (one shifted bitwise 16 places)
    private static final String BOUNCY_CASTLE_PROVIDER = "BC";
    private static final int HASH_ALGORITHM_TAGS = HashAlgorithmTags.SHA256;

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private PGPSecretKeyRingCollection pgpSec;
    private char[] password;
    private PGPSignatureGenerator mySignatureGenerator;
    private PGPPublicKey partnerPublicKey;

    private PGPHelper(String partnerPublicKeyFilePath, String mySecretKeyFilePath, String myPassPhrase) throws Exception {
        try (FileInputStream priStream = new FileInputStream(new File(mySecretKeyFilePath));
             FileInputStream pubStream = new FileInputStream(new File(partnerPublicKeyFilePath))) {
            // Step 1: parse partner public key
            PGPPublicKeyRingCollection publicSpec = new PGPPublicKeyRingCollection(PGPUtil.getDecoderStream(pubStream));
            PGPPublicKeyRing pkRing;
            Iterator it = publicSpec.getKeyRings();
            while (it.hasNext()) {
                pkRing = (PGPPublicKeyRing) publicSpec.getKeyRings().next();
                Iterator pkIt = pkRing.getPublicKeys();
                while (pkIt.hasNext()) {
                    PGPPublicKey key = (PGPPublicKey) pkIt.next();
                    if (key.isEncryptionKey()) {
                        partnerPublicKey = key;
                        break;
                    }
                }
                if (partnerPublicKey != null)
                    break;
            }

            // Step 2: parse my pgp private key
            this.pgpSec = new PGPSecretKeyRingCollection(PGPUtil.getDecoderStream(priStream));
            this.password = myPassPhrase.toCharArray();
            PGPPrivateKey myPGPPrivateKey = null;
            PGPPublicKey myPGPPublicKey = null;
            while (this.pgpSec.getKeyRings().hasNext()) {
                Object readData = this.pgpSec.getKeyRings().next();
                if (readData instanceof PGPSecretKeyRing) {
                    PGPSecretKeyRing pbr = (PGPSecretKeyRing) readData;
                    PGPSecretKey myPgpSecretKey = pbr.getSecretKey();
                    myPGPPrivateKey = myPgpSecretKey.extractPrivateKey(password, BOUNCY_CASTLE_PROVIDER);
                    myPGPPublicKey = myPgpSecretKey.getPublicKey();
                    break;
                }
            }

            // Step 3: generate my pgp signature generator
            if (myPGPPrivateKey == null || myPGPPublicKey == null) {
                throw new Exception("My PGP Key pair is invalid");
            }
            int algorithm = myPGPPublicKey.getAlgorithm();
            PGPSignatureGenerator generator = new PGPSignatureGenerator(algorithm, HASH_ALGORITHM_TAGS, BOUNCY_CASTLE_PROVIDER);
            generator.initSign(PGPSignature.BINARY_DOCUMENT, myPGPPrivateKey);
            String userId = (String) myPGPPublicKey.getUserIDs().next();
            PGPSignatureSubpacketGenerator spGen = new PGPSignatureSubpacketGenerator();
            spGen.setSignerUserID(false, userId);
            generator.setHashedSubpackets(spGen.generate());
            this.mySignatureGenerator = generator;

            LOGGER.info("Initial PGP Helper Successful!");
        }
    }

    public static synchronized void init(String partnerPublicKeyFilePath, String mySecretKeyFilePath,
                                         String myPassPhrase) throws Exception {
        INSTANCE = new PGPHelper(partnerPublicKeyFilePath, mySecretKeyFilePath, myPassPhrase);
    }

    public static PGPHelper getInstance() {
        return INSTANCE;
    }

    private PGPPrivateKey findSecretKey(long keyID) throws PGPException, NoSuchProviderException {
        PGPSecretKey pgpSecKey = pgpSec.getSecretKey(keyID);
        if (pgpSecKey == null) {
            return null;
        }
        return pgpSecKey.extractPrivateKey(password, "BC");
    }

    // Used partner publicKey to encrypt data.
    public String encrypt(byte[] data) throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             OutputStream dataOutputStream = new DataOutputStream(out);
             ByteArrayOutputStream bOut = new ByteArrayOutputStream()){

            // compress data
            PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(PGPCompressedDataGenerator.ZIP);
            try {
                PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();
                OutputStream pOut = lData.open(comData.open(bOut), PGPLiteralData.BINARY, "temp", data.length, new Date());
                pOut.write(data);
            } finally {
                comData.close();
            }

            // Encrypt data
            PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(PGPEncryptedDataGenerator.CAST5, new SecureRandom(), "BC");
            cPk.addMethod(partnerPublicKey);
            byte[] bytes = bOut.toByteArray();
            try (OutputStream cOut = cPk.open(dataOutputStream, bytes.length)) {
                cOut.write(bytes);
            }
            return Base64.getEncoder().encodeToString(out.toByteArray());
        }
    }

    public String decrypt(byte[] encryptedData) throws Exception {
        InputStream bais = new ByteArrayInputStream(encryptedData);
        bais = PGPUtil.getDecoderStream(bais);

        PGPObjectFactory pgpF = new PGPObjectFactory(bais);
        PGPEncryptedDataList enc;
        Object o = pgpF.nextObject();
        if (o instanceof PGPEncryptedDataList) {
            enc = (PGPEncryptedDataList) o;
        } else {
            enc = (PGPEncryptedDataList) pgpF.nextObject();
        }
        Iterator it = enc.getEncryptedDataObjects();
        PGPPrivateKey privateKey = null;
        PGPPublicKeyEncryptedData publicKeyEncryptedData = null;
        while (privateKey == null && it.hasNext()) {
            publicKeyEncryptedData = (PGPPublicKeyEncryptedData) it.next();
            privateKey = findSecretKey(publicKeyEncryptedData.getKeyID());
        }

        if (privateKey == null) {
            throw new IllegalArgumentException("secret key for message not found.");
        }

        InputStream clear = publicKeyEncryptedData.getDataStream(privateKey, "BC");
        PGPObjectFactory plainFact = new PGPObjectFactory(clear);
        Object message = plainFact.nextObject();
        if (message instanceof PGPCompressedData) {
            PGPCompressedData cData = (PGPCompressedData) message;
            PGPObjectFactory pgpFact = new PGPObjectFactory(cData.getDataStream());
            message = pgpFact.nextObject();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (message instanceof PGPLiteralData) {
            PGPLiteralData ld = (PGPLiteralData) message;
            InputStream unc = ld.getInputStream();
            int ch;
            while ((ch = unc.read()) >= 0) {
                baos.write(ch);
            }
        } else if (message instanceof PGPOnePassSignatureList) {
            throw new PGPException("encrypted message contains a signed message - not literal data.");
        } else {
            throw new PGPException("message is not a simple encrypted file - type unknown.");
        }
        return new String(baos.toByteArray());
    }


}
