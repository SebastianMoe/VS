package Übung1;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Parkhaus {
    private int parkingSpaces;
    private Deque<Auto> parkedCars;
    ReentrantLock monitor = new ReentrantLock();
    Condition carProducer = monitor.newCondition();
    Condition carDealer = monitor.newCondition();

    public Parkhaus(int parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
        this.parkedCars = new ArrayDeque<>();
    }

    public int getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(int parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public void parkCar(Auto auto){
        synchronized(this) {
            while(parkingSpaces == 0){
                try {
                    System.out.println("Warten an Schranke: " + auto.getLicensePlate());
                    this.wait();
                } catch (InterruptedException e) {
                    // handle exception - ignore
                }
            }
            System.out.println("Einfahrt: " + auto.getLicensePlate());
            parkingSpaces--;
            this.notifyAll();
        }
    }

    public void unparkCar(Auto auto){
        synchronized(this) {
            while (parkingSpaces > 8) {
                try {
                    System.out.println("Warten auf weitere Autos: " + auto.getLicensePlate());
                    this.wait();
                } catch (InterruptedException e) {
                    // handle exception - ignore
                }
            }
            System.out.println("Ausfahrt: " + auto.getLicensePlate());
            parkingSpaces++;
            this.notifyAll();
        }
    }

    public void parkCar2(Auto auto){ {
            monitor.lock();
            try {
                while(parkedCars.size() == parkingSpaces){
                    System.out.println("Warten auf Parkplatz: " + auto.getLicensePlate());
                    carProducer.await();
                }
                System.out.println("Abstellen: " + auto.getLicensePlate());
                parkedCars.add(auto);
                carDealer.signalAll();
            } catch (InterruptedException e) {
                // handle exception - ignore
            } finally {
                monitor.unlock();
            }
        }
    }

    public void unparkCar2(){ {
            monitor.lock();
            try {
                while (parkedCars.isEmpty()) {
                    System.out.println("Warten auf weitere Autos");
                    carDealer.await();
                }
                Auto auto = parkedCars.remove();
                System.out.println("Auto abgeholt: " + auto.getLicensePlate());

                carProducer.signalAll();
            } catch (InterruptedException e) {
                // handle exception - ignore
            } finally {
                monitor.unlock();
            }
        }
    }
}