package Übung2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class KitchenCounter {
    private int maxMeals;
    private int currentMeals = 0;

    ReentrantLock monitor = new ReentrantLock();
    Condition leereTheke = monitor.newCondition();
    Condition volleTheke = monitor.newCondition();

    public KitchenCounter(int maxMeals) {
        this.maxMeals = maxMeals;
    }

    public void put() {
        monitor.lock();
        try {
            while(currentMeals > maxMeals) {
                try {
                    System.out.println("Theke voll");
                    volleTheke.await();
                } catch (InterruptedException e) {
                    // handle exception - ignore
                }
            }
            currentMeals++;
            leereTheke.signal();
        } finally {
            monitor.unlock();
        }
    }

    public void take() {
        monitor.lock();
        try {
            while(currentMeals == 0) {
                try {
                    System.out.println("Theke leer");
                    leereTheke.await();
                } catch (InterruptedException e) {
                    // handle exception - ignore
                }
            }
            currentMeals--;
            volleTheke.signal();
        } finally {
            monitor.unlock();
        }
    }
}
