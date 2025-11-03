package Aufgabenvorstellung1;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketDistributorSimple {
    private Deque<Ticket> highPriorityTickets = new ArrayDeque<>();
    private Deque<Ticket> lowPriorityTickets = new ArrayDeque<>();
    
    private int maxCapacityPerQueue; 
    
    private ReentrantLock lock = new ReentrantLock();
    
    private Condition ticketsAvailable = lock.newCondition();
    private Condition spaceFree = lock.newCondition();
    
    public TicketDistributorSimple(int maxCapacityPerQueue) {
        highPriorityTickets = new ArrayDeque<>();
        lowPriorityTickets = new ArrayDeque<>();
        this.maxCapacityPerQueue = maxCapacityPerQueue;
    }

    public void createTicket(Ticket ticket) throws InterruptedException {
        lock.lock();
        try {
            switch(ticket.getPrio()) {
                case High:
                    while (highPriorityTickets.size() >= maxCapacityPerQueue) {
                        System.out.println("[Customer] HIGH-Queue full - waiting...");
                        spaceFree.await();
                    }
                    highPriorityTickets.add(ticket);
                    System.out.println("[Customer] HIGH-Ticket created: " + ticket.getTitle());
                    ticketsAvailable.signal(); // Wake up employee
                    break;
                    
                case Low:
                    while (lowPriorityTickets.size() >= maxCapacityPerQueue) {
                        System.out.println("[Customer] LOW-Queue full - waiting...");
                        spaceFree.await();
                    }
                    lowPriorityTickets.add(ticket);
                    System.out.println("[Customer] LOW-Ticket created: " + ticket.getTitle());
                    ticketsAvailable.signal();
                    break;
            }
        } finally {
            lock.unlock();
        }
    }
    
    
    public void processTicket() throws InterruptedException {
        lock.lock();
        try {
            while (highPriorityTickets.isEmpty() && lowPriorityTickets.isEmpty()) {
                System.out.println("[Employee] All queues empty - waiting...");
                ticketsAvailable.await();
            }
            
            if (!highPriorityTickets.isEmpty()) {
                Ticket ticket = highPriorityTickets.removeFirst();
                System.out.println("[Employee] HIGH-Ticket processed: " + ticket.getTitle());
                spaceFree.signal();
            } 
            else if (!lowPriorityTickets.isEmpty()) {
                Ticket ticket = lowPriorityTickets.removeFirst();
                System.out.println("[Employee] LOW-Ticket processed: " + ticket.getTitle());
                spaceFree.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}
