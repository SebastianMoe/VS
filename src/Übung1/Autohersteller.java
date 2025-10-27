package Übung1;

public class Autohersteller implements Runnable {
    private Parkhaus parkhaus;
    private int carCount = 0;

    public Autohersteller(Parkhaus parkhaus) {
        this.parkhaus = parkhaus;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(190);
                carCount++;
                Auto auto = new Auto("Auto " + carCount);

                parkhaus.parkCar2(auto);
                
            } catch (InterruptedException e) {
                // handle exception - ignore
            }
        }
    }
}