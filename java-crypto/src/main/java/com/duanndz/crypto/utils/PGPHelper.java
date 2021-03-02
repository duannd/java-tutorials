package com.duanndz.crypto.utils;

import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.jcajce.JcaPGPObjectFactory;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.PublicKeyDataDecryptorFactory;
import org.bouncycastle.openpgp.operator.jcajce.*;
import org.bouncycastle.util.io.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;
import java.util.Date;

/**
 * Created By duan.nguyen at 4/20/20 6:26 PM
 */
public final class PGPHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PGPHelper.class);
    private static PGPHelper INSTANCE;
    private static final String BOUNCY_CASTLE_PROVIDER = "BC";
    private static final int HASH_ALGORITHM_TAGS = HashAlgorithmTags.SHA256;

    static {
        try {
            // Security.addProvider(new BouncyCastleProvider());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // private PGPSecretKey myPgpSecretKey;
    private PGPPrivateKey myPgpPrivateKey;
    private PGPPublicKey myPgpPublicKey;
    private PGPPublicKey partnerPublicKey;

    private PGPHelper(String partnerPublicKeyFilePath, String mySecretKeyFilePath, String myPassPhrase) throws Exception {
        try (FileInputStream priStream = new FileInputStream(new File(mySecretKeyFilePath));
             FileInputStream pubStream = new FileInputStream(new File(partnerPublicKeyFilePath))) {
            // Step 1: parse partner public key
            PGPPublicKeyRingCollection publicSpec = new PGPPublicKeyRingCollection(PGPUtil
                .getDecoderStream(pubStream), new JcaKeyFingerprintCalculator());
            while (publicSpec.getKeyRings().hasNext()) {
                PGPPublicKeyRing pkRing = publicSpec.getKeyRings().next();
                while (pkRing.getPublicKeys().hasNext()) {
                    PGPPublicKey key = pkRing.getPublicKeys().next();
                    if (key.isEncryptionKey()) {
                        partnerPublicKey = key;
                        break;
                    }
                }
                if (partnerPublicKey != null)
                    break;
            }

            // Step 2: parse my pgp privateKey/publicKey
            PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(PGPUtil
                .getDecoderStream(priStream), new JcaKeyFingerprintCalculator());
            char[] password = myPassPhrase.toCharArray();
            while (pgpSec.getKeyRings().hasNext()) {
                PGPSecretKeyRing pbr = pgpSec.getKeyRings().next();
                if (pbr != null) {
                    PGPSecretKey myPgpSecretKey = pbr.getSecretKey();
                    JcePBESecretKeyDecryptorBuilder builder = new JcePBESecretKeyDecryptorBuilder()
                        .setProvider(BOUNCY_CASTLE_PROVIDER);
                    PBESecretKeyDecryptor secretKeyDecryptor = builder.build(password);
                    this.myPgpPrivateKey = myPgpSecretKey.extractPrivateKey(secretKeyDecryptor);
                    this.myPgpPublicKey = myPgpSecretKey.getPublicKey();
                    break;
                }
            }
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

    // Used partner publicKey to encrypt data.
    public String encrypt(byte[] data) throws Exception {
        try (ByteArrayOutputStream bOut = new ByteArrayOutputStream();
             ByteArrayOutputStream encOut = new ByteArrayOutputStream()) {
            // compress data
            PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();
            OutputStream pOut = lData.open(bOut, PGPLiteralData.BINARY, PGPLiteralData.CONSOLE, data.length, new Date());
            pOut.write(data);
            pOut.close();

            byte[] plainText = bOut.toByteArray();

            JcePGPDataEncryptorBuilder encryptorBuilder = new JcePGPDataEncryptorBuilder(HASH_ALGORITHM_TAGS)
                .setWithIntegrityPacket(true)
                .setSecureRandom(new SecureRandom())
                .setProvider(BOUNCY_CASTLE_PROVIDER);

            PGPEncryptedDataGenerator encGen = new PGPEncryptedDataGenerator(encryptorBuilder);
            encGen.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(partnerPublicKey)
                .setProvider(BOUNCY_CASTLE_PROVIDER));

            OutputStream cOut = encGen.open(encOut, plainText.length);
            cOut.write(plainText);
            cOut.close();

            return Base64.getEncoder().encodeToString(encOut.toByteArray());
        }
    }

    // Partner will be use your publicKey to encrypt data and You need to use private key to decrypt data.
    public String decrypt(byte[] pgpEncryptedData) throws Exception {
        PGPObjectFactory pgpFact = new JcaPGPObjectFactory(pgpEncryptedData);
        PGPEncryptedDataList encList = (PGPEncryptedDataList) pgpFact.nextObject();
        PGPPublicKeyEncryptedData encData = (PGPPublicKeyEncryptedData) encList.get(0);

        PublicKeyDataDecryptorFactory dataDecryptorFactory = new JcePublicKeyDataDecryptorFactoryBuilder()
            .setProvider(BOUNCY_CASTLE_PROVIDER)
            .build(myPgpPrivateKey);

        InputStream clear = encData.getDataStream(dataDecryptorFactory);
        byte[] literalData = Streams.readAll(clear);
        if (encData.verify()) {
            PGPObjectFactory litFact = new JcaPGPObjectFactory(literalData);
            PGPLiteralData litData = (PGPLiteralData) litFact.nextObject();
            byte[] data = Streams.readAll(litData.getInputStream());
            return new String(data);
        }
        throw new IllegalStateException("modification check failed");
    }

    // Using your private key to sign data.
    public String sign(byte[] data) throws Exception {
        try (ByteArrayOutputStream bOut = new ByteArrayOutputStream()) {

            JcaPGPContentSignerBuilder signerBuilder = new JcaPGPContentSignerBuilder(myPgpPublicKey.getAlgorithm(),
                PGPUtil.SHA256).setProvider(BOUNCY_CASTLE_PROVIDER);

            PGPSignatureGenerator sGen = new PGPSignatureGenerator(signerBuilder);
            sGen.init(PGPSignature.BINARY_DOCUMENT, myPgpPrivateKey);

            BCPGOutputStream bcOut = new BCPGOutputStream(bOut);
            sGen.generateOnePassVersion(false).encode(bcOut);

            PGPLiteralDataGenerator lGen = new PGPLiteralDataGenerator();
            OutputStream lOut = lGen.open(bcOut, PGPLiteralData.BINARY, "_CONSOLE", data.length, new Date());

            for (int i = 0; i != data.length; i++) {
                lOut.write(data[i]);
                sGen.update(data[i]);
            }
            lGen.close();

            sGen.generate().encode(bcOut);
            return Base64.getEncoder().encodeToString(bOut.toByteArray());
        }
    }

    // Partner will build signature and you need to verify signature by partner publicKey
    public boolean verifySign(byte[] pgpSignedData) throws Exception {
        JcaPGPObjectFactory pgpFact = new JcaPGPObjectFactory(pgpSignedData);
        PGPOnePassSignatureList onePassList = (PGPOnePassSignatureList) pgpFact.nextObject();
        PGPOnePassSignature ops = onePassList.get(0);
        PGPLiteralData literalData = (PGPLiteralData) pgpFact.nextObject();
        InputStream dIn = literalData.getInputStream();

        ops.init(new JcaPGPContentVerifierBuilderProvider().setProvider(BOUNCY_CASTLE_PROVIDER), partnerPublicKey);
        int ch;
        while ((ch = dIn.read()) >= 0) {
            ops.update((byte) ch);
        }
        PGPSignatureList sigList = (PGPSignatureList) pgpFact.nextObject();
        PGPSignature sig = sigList.get(0);
        return ops.verify(sig);
    }

}
