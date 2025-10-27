package Übung1;

import java.util.Random;

public class Auto implements Runnable {
    private String licensePlate;
    private Parkhaus parkhaus;

    public Auto(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Auto(String licensePlate, Parkhaus parkhaus) {
        this.licensePlate = licensePlate;
        this.parkhaus = parkhaus;
    }

    @Override
    public void run() {
        while (true){
            try {
                int seconds = new Random().nextInt(11);
                Thread.sleep(seconds * 1000);

                parkhaus.parkCar(this);

                seconds = new Random().nextInt(11);
                Thread.sleep(seconds * 1000);

                parkhaus.unparkCar(this);
            } catch (InterruptedException e) {
                // handle exception - ignore
            }
        }
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Parkhaus getParkhaus() {
        return parkhaus;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    
}