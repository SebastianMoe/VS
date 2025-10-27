package Übung1;

import java.util.Random;

public class Autohaus implements Runnable {
    private Parkhaus parkhaus;

    public Autohaus(Parkhaus parkhaus) {
        this.parkhaus = parkhaus;
    }

    @Override
    public void run() {
        while (true){
            try {
                int seconds = new Random().nextInt(4);
                Thread.sleep(seconds * 1000);

                parkhaus.unparkCar2();

            } catch (InterruptedException e) {
                // handle exception - ignore
            }
        }
    }

    public Parkhaus getParkhaus() {
        return parkhaus;
    }
}