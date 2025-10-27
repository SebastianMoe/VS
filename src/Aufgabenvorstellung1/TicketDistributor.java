package Aufgabenvorstellung1;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketDistributor {
    private Deque<Ticket> highPriorityTickets = new ArrayDeque<>();
    private Deque<Ticket> lowPriorityTickets = new ArrayDeque<>();
    
    private int maxCapacityPerQueue; 
    
    private ReentrantLock highLock = new ReentrantLock();
    private ReentrantLock lowLock = new ReentrantLock();
    
    private Condition highAvailable = highLock.newCondition();
    private Condition highSpaceFree = highLock.newCondition();
    
    private Condition lowAvailable = lowLock.newCondition();
    private Condition lowSpaceFree = lowLock.newCondition();
    
    public TicketDistributor(int maxCapacityPerQueue) {
        highPriorityTickets = new ArrayDeque<>();
        lowPriorityTickets = new ArrayDeque<>();
        this.maxCapacityPerQueue = maxCapacityPerQueue;
    }

    // Customer creates ticket
    public void createTicket(Ticket ticket) throws InterruptedException {
        switch(ticket.getPrio()) {
            case High:
                highLock.lock();
                try {
                    while (highPriorityTickets.size() >= maxCapacityPerQueue) {
                        System.out.println("[Customer] HIGH-Queue full - waiting...");
                        highSpaceFree.await();
                    }
                    highPriorityTickets.add(ticket);
                    System.out.println("[Customer] HIGH-Ticket created: " + ticket.getTitle());
                    highAvailable.signal(); // Wake up employee
                } finally {
                    highLock.unlock();
                }
                break;
                
            case Low:
                lowLock.lock();
                try {
                    while (lowPriorityTickets.size() >= maxCapacityPerQueue) {
                        System.out.println("[Customer] LOW-Queue full - waiting...");
                        lowSpaceFree.await();
                    }
                    lowPriorityTickets.add(ticket);
                    System.out.println("[Customer] LOW-Ticket created: " + ticket.getTitle());
                    lowAvailable.signal();
                } finally {
                    lowLock.unlock();
                }
                break;
        }
    }
    
    
    // Employee processes tickets (priority order)
    public Ticket processTicket() throws InterruptedException {
        while (true) {
            highLock.lock();
            try {
                if (!highPriorityTickets.isEmpty()) {
                    Ticket ticket = highPriorityTickets.removeFirst();
                    System.out.println("[Employee] HIGH-Ticket processed: " + ticket.getTitle());
                    highSpaceFree.signal(); 
                    return ticket;
                }
            } finally {
                highLock.unlock();
            }
            
            lowLock.lock();
            try {
                if (!lowPriorityTickets.isEmpty()) {
                    Ticket ticket = lowPriorityTickets.removeFirst();
                    System.out.println("[Employee] LOW-Ticket processed: " + ticket.getTitle());
                    lowSpaceFree.signal();
                    return ticket;
                }
                
                System.out.println("[Employee] All queues empty - waiting...");
                lowAvailable.await();
                
            } finally {
                lowLock.unlock();
            }
        }
    }
}
