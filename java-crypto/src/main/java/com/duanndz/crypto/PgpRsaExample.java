package com.duanndz.crypto;

import com.duanndz.crypto.utils.RSA;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;
import java.util.List;

/**
 * Created By duan.nguyen at 3/1/21 3:02 PM
 */
public class PgpRsaExample {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // Read rsa keypair
        ClassLoader classLoader = PgpRsaExample.class.getClassLoader();
        String myRSAKeypairFile = classLoader.getResource("smartpay.p12").getPath();
        String partnerCertFile = classLoader.getResource("Napas_TGTT_Public.cer").getPath();
        RSA.init(partnerCertFile, myRSAKeypairFile, "123456", "smartpay");
        String keyMap = "napas_private_key";
        RSA.addPrivateKey("napas_private_key.pem", keyMap);

        List<String> lines = Files.readAllLines(Paths.get("030121_BNB_SMARTPAY_971017_1_TC_SWC.zip.pgp"));
        String sessionInBase64 = lines.get(0)
            .replaceAll("\n", "")
            .replaceAll("\r", "");
        lines.remove(0);
        String base64InStr = String.join("", lines);
//
        byte[] decrytedSession = RSA.getInstance().decrypt(keyMap, Base64.getDecoder().decode(sessionInBase64));
        System.out.println(decrytedSession.length);
        // System.out.println(new String(decrytedSession));

        SecretKeySpec keySpec = new SecretKeySpec(decrytedSession, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NOPADDING");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        String data = new String(cipher.doFinal(Base64.getDecoder().decode(base64InStr)));
        System.out.println(data);

//        byte[] data = Files.readAllBytes(Paths.get("020321_ISS_SMARTPAY_971017_1_TC_SWC.asc"));
        // InputStream decryptingFile = new FileInputStream("020321_ISS_SMARTPAY_971017_1_TC_SWC.zip");
//        byte[] decryptedData = PGPUtils.getInstance().extractRsaEncryptedObject(base64InStr.getBytes());
//        OutputStream out = new FileOutputStream("test-d.txt");
//        out.write(decryptedData);
//        out.close();
//        System.out.println("Decrypted Done");
    }

    // 256 bits AES secret key
    public static SecretKey getAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

}
