package Aufgabenvorstellung1;

public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        TicketDistributor distributor = new TicketDistributor(10);

        for(int i = 1; i <= 5; i++) {
            Thread threadCustomer = new Thread(new Customer("Customer " + i, distributor));
            threadCustomer.setDaemon(true);
            threadCustomer.start();
        }

        for(int i = 1; i <= 5; i++) {
            Thread threadAutohaus = new Thread(new Employee(distributor));
            threadAutohaus.setDaemon(true);
            threadAutohaus.start();
        }

        Thread.sleep(60000);
        System.out.println("Simulation beendet.");
        
    }
}