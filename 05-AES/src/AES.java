import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;

public class AES {

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "GianniTheGOAT";

    public static void main(String[] args) {
        String msgs[] = {"Lorem ipsum dicet",
                    "Hola Andrés cómo está tu cuñado",
                    "Àgora ïlla Ôtto"};
        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];

            byte[] bXifrats = null;
            String desxifrat = "";
            try {
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifratAES(bXifrats, CLAU);
            } catch (Exception e) {
                System.err.println("Error de xifrat: "
                    + e.getLocalizedMessage());
            }
            System.out.println("-------------------");
            System.out.println("Msg: " + msg);
            if (bXifrats != null) {
                System.out.println("Enc: " + new String(bXifrats));
            }
            System.out.println("DEC: " + desxifrat);
        }
    }

    public static byte[] xifraAES(String msg, String clau) throws Exception {
        byte[] msgBytes = msg.getBytes("UTF-8");
        
        byte[] ivBytes = generaIv();
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        
        SecretKeySpec keySpec = generaHash(clau);
        
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] msgXifrat = cipher.doFinal(msgBytes);
        
        byte[] combined = new byte[MIDA_IV + msgXifrat.length];
        System.arraycopy(ivBytes, 0, combined, 0, MIDA_IV);
        System.arraycopy(msgXifrat, 0, combined, MIDA_IV, msgXifrat.length);
        
        return combined;
    }

    public static String desxifratAES(byte[] blvMsgXifrat, String clau) throws Exception {
        byte[] ivBytes = Arrays.copyOfRange(blvMsgXifrat, 0, MIDA_IV);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        
        byte[] msgXifrat = Arrays.copyOfRange(blvMsgXifrat, MIDA_IV, blvMsgXifrat.length);
        
        SecretKeySpec keySpec = generaHash(clau);
        
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] desxifratBytes = cipher.doFinal(msgXifrat);
        
        return new String(desxifratBytes, "UTF-8");
    }

    private static byte[] generaIv() {
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

    private static SecretKeySpec generaHash(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = digest.digest(password.getBytes("UTF-8"));
        return new SecretKeySpec(hash, ALGORISME_XIFRAT);
    }
}