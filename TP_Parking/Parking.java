/**
 *
 * @author armand
 */
public class Parking {
    int nbPlaces;
    int nbPlacesOccupees;
    Voiture[] places; // Tableau de voitures garées

    public Parking(int nbPlaces) {
        this.nbPlaces = nbPlaces;
        this.nbPlacesOccupees = 0;
        this.places = new Voiture[nbPlaces];
    }

    
}
