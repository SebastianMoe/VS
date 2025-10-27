package Übung2;

import java.util.Random;

public class Student implements Runnable {
    private KitchenCounter kitchenCounter;
    private String name;

    public Student(KitchenCounter kitchenCounter, String name) {
        this.kitchenCounter = kitchenCounter;
        this.name = name;
    }

    @Override
    public void run() {
        while (true){
            try {
                int seconds = new Random().nextInt(4);
                Thread.sleep(seconds * 1000);

                kitchenCounter.take();
                System.out.println(name + ": Leberkässemmel abgenommen");

            } catch (InterruptedException e) {
                // handle exception - ignore
            }
        }
    }
}
