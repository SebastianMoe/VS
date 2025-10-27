package Aufgabenvorstellung1;

public class Ticket {
    private TicketType prio;
    private String title;

    public Ticket(TicketType prio, String title) {
        this.prio = prio;
        this.title = title;
    }

    public TicketType getPrio() {
        return prio;
    }

    public String getTitle() {
        return title;
    }

    public void setPrio(TicketType prio) {
        this.prio = prio;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
