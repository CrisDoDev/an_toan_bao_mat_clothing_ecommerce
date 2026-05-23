package util;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SignatureUtil {

    // 1. Ham build chuoi chuan hoa va bam SHA-256 (Khong dung ten san pham realtime)
    public static String buildOrderHash(int orderId, int userId, double totalMoney, String rawDetailsStr) {
        try {
            // Ghep cac thong tin con so co dinh thanh chuoi duy nhat
            String rawData = "orderId=" + orderId + "&userId=" + userId + "&total=" + totalMoney + "&items=" + rawDetailsStr;
            
            // Tien hanh bam SHA-256 ra chuoi Hex 64 ky tu
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(rawData.getBytes(StandardCharsets.UTF_8));
            
            // Chuyen tu mang byte sang chuoi Hex chu thuong truyen thong
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hashBytes.length; i++) {
                sb.append(String.format("%02x", hashBytes[i]));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 2. Ham xac thuc chu ky so dung PublicKey (Xay dung tren nen tang code cua thay)
    public static boolean verifySignature(String orderHash, String signatureBase64, String publicKeyBase64) {
        try {
            // Phuc hoi PublicKey tu Base64 text
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyBase64);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey publicKey = kf.generatePublic(spec);

            // Phuc hoi mang bytes cua chu ky tu Base64
            byte[] sigBytes = Base64.getDecoder().decode(signatureBase64);

            // Thuc hien khoi tao verify giong code cua thay
            Signature sigEngine = Signature.getInstance("SHA256withRSA");
            sigEngine.initVerify(publicKey);
            sigEngine.update(orderHash.getBytes(StandardCharsets.UTF_8));

            return sigEngine.verify(sigBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}