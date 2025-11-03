package Aufgabenvorstellung1;

import java.util.Random;

public class Employee implements Runnable {
    private TicketDistributorSimple distributor;
    
    public Employee(TicketDistributorSimple distributor) {
        this.distributor = distributor;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(new Random().nextInt(8) * 1000);

                distributor.processTicket();
            } catch (InterruptedException e) {
                // handle exception - ignore
            }
        }
    }
    
}
