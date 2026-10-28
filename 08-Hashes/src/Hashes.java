import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HexFormat; 
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {

    public int npass = 0; 
    private static final String CHARSET = "abcdefABCDEF1234567890!";
    private static final int MAX_LEN = 6;
    
    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
 
        Hashes h = new Hashes();
 
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt),
                h.getPBKDF2AmbSalt(pw, salt) };
        String pwTrobat = null;
        String[] algorismes = { "SHA-512", "PBKDF2" };
 
        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("===========================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("---------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");
 
            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();
 
            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("---------------------------\n\n");
        }
    }

    public String getSHA512AmbSalt(String pw, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(pw.getBytes(StandardCharsets.UTF_8));
            HexFormat hex = HexFormat.of();
            return hex.formatHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPBKDF2AmbSalt(String pw, String salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(),
                    salt.getBytes(StandardCharsets.UTF_8), 65536, 128);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] bytes = skf.generateSecret(spec).getEncoded();
            HexFormat hex = HexFormat.of();
            return hex.formatHex(bytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public String forcaBruta(String alg, String hash, String salt) {
        npass = 0;
        char[] pass = new char[MAX_LEN];
        return proba(alg, hash, salt, pass, 0);
    }

    private String proba(String alg, String hash, String salt, char[] pass, int pos) {
        for (int i = 0; i < CHARSET.length(); i++) {
            pass[pos] = CHARSET.charAt(i);
            String candidat = new String(pass, 0, pos + 1);
            npass++;
            if (calculaHash(alg, candidat, salt).equals(hash)) {
                return candidat;
            }
            if (pos + 1 < MAX_LEN) {
                String trobat = proba(alg, hash, salt, pass, pos + 1);
                if (trobat != null) {
                    return trobat;
                }
            }
        }
        return null;
    }

    private String calculaHash(String alg, String pw, String salt) {
        if (alg.equals("SHA-512")) {
            return getSHA512AmbSalt(pw, salt);
        }
        return getPBKDF2AmbSalt(pw, salt);
    }

    public String getInterval(long t1, long t2) {
        long diff = t2 - t1;
        long dies = diff / 86_400_000L;
        diff %= 86_400_000L;
        long hores = diff / 3_600_000L;
        diff %= 3_600_000L;
        long minuts = diff / 60_000L;
        diff %= 60_000L;
        long segons = diff / 1000L;
        long millis = diff % 1000L;
        return dies + " dies / " + hores + " hores / " + minuts + " minuts / "
                + segons + " segons / " + millis + " millis";
    }



}
