package Übung1;

public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        Parkhaus parkhaus = new Parkhaus(10);

        // Aufgabe 1 & 2
        /*
         * for(int i = 1; i <= 20; i++) {
            Thread thread = new Thread(new Auto("OTH-R " + i, parkhaus));
            thread.setDaemon(true);
            thread.start();
            }
         */

        // Aufgabe 3
        Thread threadAutohersteller = new Thread(new Autohersteller(parkhaus));
        threadAutohersteller.setDaemon(true);
        threadAutohersteller.start();

        for(int i = 1; i <= 5; i++) {
            Thread threadAutohaus = new Thread(new Autohaus(parkhaus));
            threadAutohaus.setDaemon(true);
            threadAutohaus.start();
        }

        Thread.sleep(30000);
        System.out.println("Simulation beendet.");
        
    }
}