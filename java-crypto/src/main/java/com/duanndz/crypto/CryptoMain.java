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
        String partnerPublicKey = CryptoMain.class.getClassLoader().getResource("partner-pub.asc").getPath();
        LOGGER.info("Partner PGP public key file path: {}", partnerPublicKey);
        PGPHelper.init(partnerPublicKey, myPrivateKeyFilePath, "123456");

        // Crypt data
        String plainText = "Duan Nguyen Dinh at ";

        for (int i = 0; i < 2; i++) {
            String data = plainText + System.currentTimeMillis();
            String encryptedDataInBase64 = PGPHelper.getInstance().encrypt(data.getBytes(StandardCharsets.UTF_8));
            LOGGER.info("Encrypt {} == \n{}", data, encryptedDataInBase64);

            // Decrypt data
            String decryptedData = PGPHelper.getInstance().decrypt(Base64.getDecoder().decode(encryptedDataInBase64));
            LOGGER.info("DecryptedData: {}, equals data: {}", decryptedData, data.equals(decryptedData));

            // Signed Data
            String pgpSignedData = PGPHelper.getInstance().sign(data.getBytes(StandardCharsets.UTF_8));
            LOGGER.info("pgpSignedData : \n{}", pgpSignedData);
            boolean isVerified = PGPHelper.getInstance().verifySign(Base64.getDecoder().decode(pgpSignedData));
            LOGGER.info("isVerified : {}", isVerified);
        }

    }

}
