public class Main {
    public static void main(String[] args) {
        Parking parking = new Parking(2);

        Voiture v1 = new Voiture(1, parking);
        Voiture v2 = new Voiture(2, parking);
        Voiture v3 = new Voiture(3, parking);
        Voiture v4 = new Voiture(4, parking);
        Voiture v5 = new Voiture(5, parking);
        Voiture v6 = new Voiture(6, parking);

        v1.start();
        v2.start();
        v3.start();
        v4.start();
        v5.start();
        v6.start();
    }
}

