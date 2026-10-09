package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {
    public static final String lletres = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    private char[] minuscules = lletres.toCharArray();
    private char[] majuscules = lletres.toUpperCase().toCharArray();

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            int desplaçament = Integer.parseInt(clau);
            if (desplaçament < 0 || desplaçament > 40) {
                throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
            }
            return new TextXifrat(xifraRotX(msg, desplaçament).getBytes());
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            int desplaçament = Integer.parseInt(clau);
            if (desplaçament < 0 || desplaçament > 40) {
                throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
            }
            return desxifraRotX(new String(xifrat.getBytes()), desplaçament);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
    }

    private String xifraRotX(String cadena, int desplaçament) {
        String resultat = "";
        for (char c : cadena.toCharArray()) {
            int posMinuscula = lletres.indexOf(c);
            int posMajuscula = lletres.toUpperCase().indexOf(c);
            if (posMinuscula != -1) {
                int novaPos = (posMinuscula + desplaçament) % minuscules.length;
                resultat = resultat + minuscules[novaPos];
            } else if (posMajuscula != -1) {
                int novaPos = (posMajuscula + desplaçament) % majuscules.length;
                resultat = resultat + majuscules[novaPos];
            } else {
                resultat = resultat + c;
            }
        }
        return resultat;
    }

    private String desxifraRotX(String cadena, int desplaçament) {
        String resultat = "";
        for (char c : cadena.toCharArray()) {
            int posMinuscula = lletres.indexOf(c);
            int posMajuscula = lletres.toUpperCase().indexOf(c);
            if (posMinuscula != -1) {
                int novaPos = (posMinuscula - desplaçament + minuscules.length) % minuscules.length;
                resultat = resultat + minuscules[novaPos];
            } else if (posMajuscula != -1) {
                int novaPos = (posMajuscula - desplaçament + majuscules.length) % majuscules.length;
                resultat = resultat + majuscules[novaPos];
            } else {
                resultat = resultat + c;
            }
        }
        return resultat;
    }

}