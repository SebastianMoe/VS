package Übung3.Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Thread zum Senden von Nachrichten zum Server.
 * Liest von der Tastatur und sendet zum Server.
 */
public class MessageSender implements Runnable {
    private PrintWriter out;
    private Socket socket;
    private volatile boolean running = true;
    
    public MessageSender(PrintWriter out, Socket socket) {
        this.out = out;
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            
            while (running) {
                String message = scanner.nextLine();
                
                out.println(message);
                
                if (message.equalsIgnoreCase("Bye")) {
                    System.out.println("Chat beendet durch Benutzer.");
                    running = false;
                    
                    try {
                        socket.close();
                    } catch (IOException e) {
                    }
                    break;
                }
            }
            
        } catch (Exception e) {
            if (running) {
                System.err.println("Sendefehler: " + e.getMessage());
            }
        }
    }
    
    public void stop() {
        running = false;
    }
}
