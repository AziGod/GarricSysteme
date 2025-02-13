import java.io.File;
import java.util.Scanner;

public class Associatif {

    public static void main(String[] args) {


        int n = 4;
        int lignes = (int) Math.pow(2, n);
        int bloc = 32;
        int nbEntrees = 2;
        int IndexLigne;
        int IndexColonne;
        int hit = 0;
        int fail = 0;

        try {

            int[][] cache = new int [lignes][nbEntrees];
            int[][] LRU = new int [lignes][nbEntrees];

            File file = new File("/home/alecourt/GarricSysteme/data/matrice10.txt");

            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                System.out.println(line);

                int adresse = Integer.parseInt(line.substring(0, 7)); // Récupère l'adresse "2293604"
                int deplacement = adresse%bloc;
                int b = adresse/bloc;
                int ligne = b%8;
                int etiquette = b/8;

                System.out.println("Adresse : " + adresse);
                System.out.println("Déplacement : " + deplacement);
                System.out.println("Ligne : " + ligne);
                System.out.println("Etiquette : " + etiquette);


                //for (IndexLigne = 0; IndexLigne < lignes; IndexLigne++) {
                    //for (IndexColonne = 0; IndexColonne < nbEntrees; IndexColonne++) {
                        //if (cache[IndexLigne][IndexColonne] == etiquette) {
                            //hit += 1;
                        //} else {
                            //cache[IndexLigne][IndexColonne] = etiquette;
                            //fail += 1;
                        //}
                    //}
                //}

                
            }
            input.close();

            System.out.println("Hit: " + hit); 
            System.out.println("Fail: " + fail);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}