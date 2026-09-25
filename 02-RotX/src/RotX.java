public class RotX {
    public final String lletres = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public char[] minuscules = lletres.toCharArray();
    public char[] majuscules = lletres.toUpperCase().toCharArray();

    public static void main(String[] args) {
        RotX rotx = new RotX();
        
        String msgs[] = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("\nXifrat\n-------");

        for (int i = 0; i < msgs.length; i++) {
            int rot = i * 2;
            msgsXifrats[i] = rotx.xifraRotX(msgs[i], rot);
            String textOrigen = "(" + rot + ")-" + msgs[i];
            System.out.printf("%-28s => %s%n", textOrigen, msgsXifrats[i]);
        }

        System.out.println("\nDesxifrat\n---------");

        for (int i = 0; i < msgsXifrats.length; i++) {
            int rot = i * 2;
            String textXifrat = "(" + rot + ") " + msgsXifrats[i];
            System.out.printf("%-28s => %s%n", textXifrat, rotx.desxifraRotX(msgsXifrats[i], rot));
        }

        System.out.println("\nMissatge xifrat: " + msgsXifrats[3] + "\n---------");

        System.out.print(rotx.forcaBrutaRotX(msgsXifrats[3]));
    }

    public String xifraRotX(String cadena, int desplaçament) {
       String resultat = "";
        for(char c: cadena.toCharArray()){
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

    public String desxifraRotX(String cadena, int desplaçament) {
        String resultat = "";
        for(char c: cadena.toCharArray()){
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

    public String forcaBrutaRotX(String cadenaXifrada) {
        String resultatTotal = "";

        for (int n = 0; n < minuscules.length; n++) {
            String liniaDesxifrada = "";

            for (char c : cadenaXifrada.toCharArray()) {
                int posMinuscula = lletres.indexOf(c);
                int posMajuscula = lletres.toUpperCase().indexOf(c);

                if (posMinuscula != -1) {
                    int novaPos = (posMinuscula - n + minuscules.length) % minuscules.length;
                    liniaDesxifrada += minuscules[novaPos];
                } else if (posMajuscula != -1) {
                    int novaPos = (posMajuscula - n + majuscules.length) % majuscules.length;
                    liniaDesxifrada += majuscules[novaPos];
                } else {
                    liniaDesxifrada += c;
                }
            }

            // Format del printf en String.format pero fer més fàcil el return
            String textForcaBruta = "(" + n + ") " + cadenaXifrada;
            resultatTotal += String.format("%-28s => %s%n", textForcaBruta, liniaDesxifrada);
        }

        return resultatTotal;
    }
}
