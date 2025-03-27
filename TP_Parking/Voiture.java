import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author armand
 */
public class Voiture extends Thread {

    private int numero;
    private Parking parking;
    private JVoiture fenetre;

    public Voiture(int numero, Parking parking) {
        this.numero = numero;
        this.parking = parking;
        this.fenetre = new JVoiture(numero, "voiture"+numero, "Je roule");
    }

    public void rouleVille(){
        // Génère un temps d'attente entre 1s et 5s
        int tempsAttente = ThreadLocalRandom.current().nextInt(1000, 5000);

        fenetre.setEtat("Je roule");

        try {
            Thread.sleep(tempsAttente);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Le thread a été interrompu.");
        }
    }

    public void seGarer() {

        int tempsAttente = ThreadLocalRandom.current().nextInt(1000, 5000);

        synchronized (parking.places) { // Protection contre les accès concurrents
            if (parking.nbPlacesOccupees == parking.nbPlaces) {

                fenetre.setEtat("J'attends une place");

                //Parking plein donc attends 1s avant de retry
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Le thread a été interrompu.");
                }
                seGarer();

            } else {
                //Parking non plein
                //Attends 1s
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Le thread a été interrompu.");
                }

                fenetre.setEtat("Je suis garé");

                //Se gare
                parking.places[parking.nbPlacesOccupees] = this; // Stocker l'objet voiture
                parking.nbPlacesOccupees++;

                //Attends entre 1s et 5s sur la place de parking
                try {
                    Thread.sleep(tempsAttente);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Le thread a été interrompu.");
                }
            }
        }
    }

    public void sortirDuParking() {

        for (int i = 0; i < parking.nbPlaces; i++) {
            if (parking.places[i] == this) {
                parking.places[i] = null; // Vide la place correctement
                parking.nbPlacesOccupees--;
                break;
            }
        }
    }



    @Override
    public void run() {
        while (true) {
            seGarer();
            sortirDuParking();
            rouleVille();
        }
    }
    
}
