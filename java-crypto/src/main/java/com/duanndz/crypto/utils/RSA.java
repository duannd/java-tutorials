package com.duanndz.crypto.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.security.cert.X509Certificate;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created By duan.nguyen at 9/25/20 3:48 PM
 */
public final class RSA {

    private static final String RSA_ALGORITHM = "SHA256withRSA";

    private static RSA instance;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final X509Certificate certificate;
    private static final Map<String, PrivateKey> HASH_MAP = new ConcurrentHashMap<>();

    private RSA(String partnerCertFilePath, String spKeyPairFilePath, String keystorePass, String alias) throws Exception {
        // Get partnerPublic key
        try (FileInputStream fileInputStream = new FileInputStream(partnerCertFilePath)) {
            this.certificate = X509Certificate.getInstance(fileInputStream);
        }

        //get key pair (private and public) from myKeyPair
        KeyStore key = KeyStore.getInstance("PKCS12");
        char[] cpassword = keystorePass.toCharArray();

        try (FileInputStream fileInputStream = new FileInputStream(spKeyPairFilePath)) {
            key.load(fileInputStream, cpassword);
        }
        this.privateKey = (PrivateKey) key.getKey(alias, cpassword);
        Certificate myCert = key.getCertificate(alias);
        this.publicKey = myCert.getPublicKey();
    }

    public static void init(String partnerCertFilePath, String spKeyPairFilePath, String spKeystorePass, String alias) throws Exception {
        instance = new RSA(partnerCertFilePath, spKeyPairFilePath, spKeystorePass, alias);
    }

    public static void addPrivateKey(String pemFile, String keyMap) throws Exception {
        String keyInBase64 = String.join("", Files.readAllLines(Paths.get(pemFile))); //abc
        keyInBase64 = keyInBase64.replaceAll("-----BEGIN RSA PRIVATE KEY-----", "")
            .replaceAll("-----END RSA PRIVATE KEY-----", "")
            .replaceAll("\n", "").replaceAll("\r", "");
        byte[] encoded = Base64.getDecoder().decode(keyInBase64);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        HASH_MAP.put(keyMap, keyFactory.generatePrivate(keySpec));
    }

    public static RSA getInstance() {
        return instance;
    }

    public RSAPublicKey getPartnerRSAPublicKey() {
        return (RSAPublicKey) this.certificate.getPublicKey();
    }

    public RSAPublicKey getMyRSAPublicKey() {
        return (RSAPublicKey) this.publicKey;
    }

    public RSAPrivateKey getMyRSAPrivateKey() {
        return (RSAPrivateKey) this.privateKey;
    }

    public byte[] decrypt(byte[] encData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/NOPADDING");
        cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        return cipher.doFinal(encData);
    }

    public byte[] decrypt(String keyMap, byte[] encData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/NOPADDING");
        // OAEPParameterSpec oaepParameterSpec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
        cipher.init(Cipher.DECRYPT_MODE, HASH_MAP.get(keyMap));
        return cipher.doFinal(encData);
    }

}
