import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Polialfabetic {

    public static final String lletres = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ";
    public static String clauSecreta = "miClaveSecreta";
    public static char[] alfabetPermutat = new char[lletres.length()];
    public static Random random = new Random();

    public static void main(String[] args) {

        String msgs[] = { "Test 01 àrbitre, coixí, Perimetre",
                "Test 02 Taüll, DÍA, año",
                "Test 03 Peça, Òrrius, Bòvila" };
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge:\n--------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("Desxifratge:\n--------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }

    public static void initRandom(String clau) {
        long seed = 0;
        for (int i = 0; i < clau.length(); i++) {
            seed = seed * 69 + clau.charAt(i);
        }
        random.setSeed(seed);
    }

    public static void permutaAlfabet() {
        ArrayList<Character> llistaLletres = new ArrayList<>();
        for (char c : lletres.toCharArray()) {
            llistaLletres.add(c);
        }

        Collections.shuffle(llistaLletres, random);

        for (int i = 0; i < llistaLletres.size(); i++) {
            alfabetPermutat[i] = llistaLletres.get(i);
        }
    }

    public static String xifraPoliAlfa(String msg) {
        StringBuilder resultat = new StringBuilder();
        for (char c : msg.toCharArray()) {

            char cUpper = Character.toUpperCase(c);
            int indexOriginal = lletres.indexOf(cUpper);

            if (indexOriginal != -1) {
                permutaAlfabet();

                char charXifrat = alfabetPermutat[indexOriginal];

                if (Character.isLowerCase(c)) {
                    resultat.append(Character.toLowerCase(charXifrat));
                } else {
                    resultat.append(charXifrat);
                }
            } else {
                resultat.append(c);
            }
        }
        return resultat.toString();
    }

    public static String desxifraPoliAlfa(String msgXifrat) {
        StringBuilder resultat = new StringBuilder();

        for (char c : msgXifrat.toCharArray()) {
            char cUpper = Character.toUpperCase(c);

            int indexLletres = lletres.indexOf(cUpper);

            if (indexLletres != -1) {

                permutaAlfabet();

                int indexPermutat = -1;
                for (int i = 0; i < alfabetPermutat.length; i++) {
                    if (alfabetPermutat[i] == cUpper) {
                        indexPermutat = i;
                        break;
                    }
                }

                if (indexPermutat != -1) {
                    char charInici = lletres.charAt(indexPermutat);

                    if (Character.isLowerCase(c)) {
                        resultat.append(Character.toLowerCase(charInici));
                    } else {
                        resultat.append(charInici);
                    }
                }
            } else {
                resultat.append(c);
            }
        }
        return resultat.toString();
    }
}
