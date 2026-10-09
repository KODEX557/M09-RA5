package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {

    public static final String lletres = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ";
    private char[] alfabetPermutat = new char[lletres.length()];
    private Random random = new Random();

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            long seed = Long.parseLong(clau);
            initRandom(seed);
            return new TextXifrat(xifraPoliAlfa(msg).getBytes());
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            long seed = Long.parseLong(clau);
            initRandom(seed);
            return desxifraPoliAlfa(new String(xifrat.getBytes()));
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau de Polialfabètic ha de ser un String convertible a long");
        }
    }

    private void initRandom(long seed) {
        random.setSeed(seed);
    }

    private void permutaAlfabet() {
        ArrayList<Character> llistaLletres = new ArrayList<>();
        for (char c : lletres.toCharArray()) {
            llistaLletres.add(c);
        }
        Collections.shuffle(llistaLletres, random);
        for (int i = 0; i < llistaLletres.size(); i++) {
            alfabetPermutat[i] = llistaLletres.get(i);
        }
    }

    private String xifraPoliAlfa(String msg) {
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

    private String desxifraPoliAlfa(String msgXifrat) {
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