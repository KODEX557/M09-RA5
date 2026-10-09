package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;

public class XifradorMonoalfabetic implements Xifrador {

    public static final String lletres = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ";
    private char[] alfabetPermutat;

    public XifradorMonoalfabetic() {
        this.alfabetPermutat = permutaAlfabet(lletres);
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        return new TextXifrat(xifraMonoAlfa(msg).getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }
        return desxifraMonoAlfa(new String(xifrat.getBytes()));
    }

    private char[] permutaAlfabet(String alfabet) {
        ArrayList<Character> llista = new ArrayList<>();
        for (char c : alfabet.toCharArray()) {
            llista.add(c);
        }
        Collections.shuffle(llista);
        char[] resultat = new char[llista.size()];
        for (int i = 0; i < llista.size(); i++) {
            resultat[i] = llista.get(i);
        }
        return resultat;
    }

    private String xifraMonoAlfa(String cadena) {
        String resultat = "";
        for (char c : cadena.toCharArray()) {
            boolean esMinus = Character.isLowerCase(c);
            char aMajus = Character.toUpperCase(c);
            int index = lletres.indexOf(aMajus);
            if (index != -1) {
                char lletraXifrada = alfabetPermutat[index];
                if (esMinus)
                    lletraXifrada = Character.toLowerCase(lletraXifrada);
                resultat += lletraXifrada;
            } else {
                resultat += c;
            }
        }
        return resultat;
    }

    private String desxifraMonoAlfa(String cadena) {
        String resultat = "";
        for (char c : cadena.toCharArray()) {
            boolean esMinus = Character.isLowerCase(c);
            char aMajus = Character.toUpperCase(c);
            int indexPermutat = -1;
            for (int i = 0; i < alfabetPermutat.length; i++) {
                if (alfabetPermutat[i] == aMajus) {
                    indexPermutat = i;
                    break;
                }
            }
            if (indexPermutat != -1) {
                char lletraOg = lletres.charAt(indexPermutat);
                if (esMinus)
                    lletraOg = Character.toLowerCase(lletraOg);
                resultat += lletraOg;
            } else {
                resultat += c;
            }
        }
        return resultat;
    }
}