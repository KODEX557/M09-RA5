package iticbcn.xifratge;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;

public class XifradorAES implements Xifrador {

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";
    private static final int MIDA_IV = 16;

    private byte[] iv = new byte[MIDA_IV];

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
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

            return new TextXifrat(combined);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(1);
            return null;
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            byte[] blvMsgXifrat = xifrat.getBytes();
            byte[] ivBytes = Arrays.copyOfRange(blvMsgXifrat, 0, MIDA_IV);
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            byte[] msgXifrat = Arrays.copyOfRange(blvMsgXifrat, MIDA_IV, blvMsgXifrat.length);

            SecretKeySpec keySpec = generaHash(clau);
            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] desxifratBytes = cipher.doFinal(msgXifrat);

            return new String(desxifratBytes, "UTF-8");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(1);
            return null;
        }
    }

    private byte[] generaIv() {
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

    private SecretKeySpec generaHash(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = digest.digest(password.getBytes("UTF-8"));
        return new SecretKeySpec(hash, ALGORISME_XIFRAT);
    }
}