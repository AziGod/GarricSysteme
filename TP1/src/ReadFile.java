import java.io.File;
import java.util.Scanner;

public class ReadFile {

    public static void main(String[] args) {


        int n = 4;
        int lignes = (int) Math.pow(2, n);
        int i = 0;
        int hit = 0;
        int fail = 0;

        try {

            int[] cache = new int [lignes]; 

            File file = new File("/home/alecourt/GarricSysteme/TP1/fichiers/matrice10.txt");

            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                System.out.println(line);

                int adresse = Integer.parseInt(line.substring(0, 7));

                int numLines = lignes;    // 2^4 lignes
                int offsetBits = 5;   // 5 bits pour l'offset (32 octet bloc soit 2^5)
                int indexBits = n;    // n bits pour l'index 
    
                int index = (adresse >> offsetBits) & (numLines - 1); // Décalage puis masque sur 4 bits
                int tag = adresse >> (offsetBits + indexBits); // Décalage restant
        
                //System.out.println("Adresse: " + adresse);
                //System.out.println("Tag: " + tag);
                //System.out.println("Index: " + index);
                //System.out.println("Offset: " + offset);


                if (i == lignes) {
                    i = 0;
                }
                
                if (cache[index] == tag) {
                    hit += 1;
                } else {
                    cache[index] = tag;
                    fail  += 1;
                }

                i+=1;

                
            }
            input.close();


            double cacheTime = 5.0;  // Temps d'accès au cache en ns
            double memoryTime = 50.0; // Temps d'accès à la mémoire en ns
    
            // Calcul du taux de hit
            double hitRate = (double) hit / (hit + fail);
    
            // Calcul du temps moyen d'accès
            double averageAccessTime = cacheTime + (1 - hitRate) * memoryTime;

            System.out.println("Hit: " + hit); 
            System.out.println("Fail: " + fail);
            System.out.println("Temps moyen d'accès (ns): " + averageAccessTime);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    

}