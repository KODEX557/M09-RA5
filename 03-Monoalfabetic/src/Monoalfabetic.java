import java.util.ArrayList;
import java.util.Collections;

public class Monoalfabetic {

    public static final String lletres = "AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ";

    public static char[] alfabetPermutat = permutaAlfabet(lletres);
    public static void main(String[] args) {
        for (char c : lletres.toCharArray()) {
            System.out.print(c + " ");
        }
        System.out.println();
        for (char c : alfabetPermutat) {
            System.out.print(c + " ");
        }
        System.out.println("\n\nXifratge:");

        String[] tests = {
            "Test 01 àrbritre, coixi, Perimetre",
            "Test 02 Taüll, DĨA, año",
            "Test 03 Peça, Örrius, Bóvila"
        };
        
        String[] msgsXifrats = new String[tests.length];

        for (int i = 0; i < tests.length; i++) {
            msgsXifrats[i] = xifraMonoAlfa(tests[i]);
            System.out.printf("%-35s -> %s%n", tests[i], msgsXifrats[i]);
        }

        System.out.println("\nDesxifratge:");

        for (int i = 0; i < msgsXifrats.length; i++) {
            System.out.printf("%-35s -> %s%n", msgsXifrats[i], tests[i]);
        }
    }

    public static char[] permutaAlfabet(String alfabet) {
        
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

    public static String xifraMonoAlfa(String cadena) {
       String resultat = "";

       for(char c : cadena.toCharArray()){
            boolean esMinus = Character.isLowerCase(c);
            char aMajus = Character.toUpperCase(c);

            int index = lletres.indexOf(aMajus);
            if(index != -1){
                char lletraXifrada = alfabetPermutat[index];
                if (esMinus) {
                    lletraXifrada = Character.toLowerCase(lletraXifrada);
                }
                resultat += lletraXifrada;
            } else {
                resultat += c;
            }
       }
       return resultat;
    }

    public static String desxifraMonoAlfa(String cadena) {
        
        String resultat = "";
        
        for(char c : cadena.toCharArray()){
            boolean esMinus = Character.isLowerCase(c);
            char aMajus = Character.toUpperCase(c);

            int indexPermutat = -1;
            for(int i = 0; i < alfabetPermutat.length; i++) {
                if (alfabetPermutat[i] == aMajus){
                    indexPermutat = i;
                    break;
                }
            }

            if(indexPermutat != -1){
                char lletraOg = lletres.charAt(indexPermutat);
                if(esMinus){
                    lletraOg = Character.toLowerCase(lletraOg);
                }
                resultat += lletraOg;
            } else {
                resultat += c;
            }  
        }
        return resultat;
    }
}
