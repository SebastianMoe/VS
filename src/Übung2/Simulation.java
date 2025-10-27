package Übung2;

public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        KitchenCounter	theke =	new KitchenCounter(4);
        new Thread( new Waiter(theke,	"Kellner-1") ).start();
        new Thread( new Waiter(theke,	"Kellner-2") ).start();
        for(int i=1;	i<=8;	i++)
            new Thread( new Student(theke,	"Student-"+i) ).start();

        Thread.sleep(30000);
        System.out.println("Simulation beendet.");
        
    }
}