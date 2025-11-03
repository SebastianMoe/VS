package Aufgabenvorstellung1;

import java.util.Random;

public class Customer implements Runnable {
    private TicketDistributorSimple distributor;
    private String name;
    private int ticketCount = 0;

    public Customer(String name, TicketDistributorSimple distributor) {
        this.name = name;
        this.distributor = distributor;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(new Random().nextInt(10) * 1000);
                ticketCount++;
                
                switch (new Random().nextInt(2)) {
                    case 0:
                        distributor.createTicket(new Ticket(TicketType.Low, "Ticket " + ticketCount + " from " + name));
                        break;
                    case 1:
                        distributor.createTicket(new Ticket(TicketType.High, "Ticket " + ticketCount + " from " + name));
                        break;
                    default:
                        break;
                }

            } catch (InterruptedException e) {
                // handle exception - ignore
            }
        }
    }
    
}
