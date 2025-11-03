package Übung3.Chat;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Thread zum Empfangen von Nachrichten vom Server.
 * Läuft parallel zum MessageSender.
 */
public class MessageReceiver implements Runnable {
    private BufferedReader in;
    private volatile boolean running = true;
    
    public MessageReceiver(BufferedReader in) {
        this.in = in;
    }
    
    @Override
    public void run() {
        try {
            String message;
            
            while (running && (message = in.readLine()) != null) {
                System.out.println(message);
                
                if (message.contains("closing") || message.contains("Goodbye")) {
                    running = false;
                    break;
                }
            }
            
        } catch (IOException e) {
            if (running) {
                System.err.println("Empfangsfehler: " + e.getMessage());
            }
        }
    }
    
    public void stop() {
        running = false;
    }
}
