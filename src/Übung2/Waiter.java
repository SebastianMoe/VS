package Übung2;

import java.util.Random;

public class Waiter implements Runnable {
    private KitchenCounter kitchenCounter;
    private String name;

    public Waiter(KitchenCounter kitchenCounter, String name) {
        this.kitchenCounter = kitchenCounter;
        this.name = name;
    }

    @Override
    public void run() {
        while (true){
            try {
                int seconds = new Random().nextInt(4);
                Thread.sleep(seconds * 1000);

                kitchenCounter.put();
                System.out.println(name + ": Leberkässemmel aufgelegt");

            } catch (InterruptedException e) {
                // handle exception - ignore
            }
        }
    }
}
