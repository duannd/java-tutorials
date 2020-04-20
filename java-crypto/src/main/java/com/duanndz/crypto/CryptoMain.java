package com.duanndz.crypto;

import com.duanndz.crypto.utils.PGPHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created By duan.nguyen at 4/20/20 6:19 PM
 */
public class CryptoMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoMain.class);

    public static void main(String[] args) throws Exception {
        String myPrivateKeyFilePath = CryptoMain.class.getClassLoader().getResource("duanndz-sec.asc").getPath();
        LOGGER.info("My PGP secret key file path: {}", myPrivateKeyFilePath);
        String partnerPublicKey = CryptoMain.class.getClassLoader().getResource("duanndz-pub.asc").getPath();
        LOGGER.info("Partner PGP public key file path: {}", partnerPublicKey);
        PGPHelper.init(partnerPublicKey, myPrivateKeyFilePath, "123456");

        // Crypt data
        String plainText = "Duan Nguyen Dinh";
        String encryptedDataInBase64 = PGPHelper.getInstance().encrypt(plainText.getBytes(StandardCharsets.UTF_8));
        LOGGER.info("Encrypt {} == \n{}", plainText, encryptedDataInBase64);

        // Decrypt data
        String decryptedData = PGPHelper.getInstance().decrypt(Base64.getDecoder().decode(encryptedDataInBase64));
        LOGGER.info("Decrypted Data: {}", decryptedData);
    }

}
