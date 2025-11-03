package Übung3.Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_HOST = "localhost"; // Lokaler Test-Server
    private static final int SERVER_PORT = 1212;
    
    public static void main(String[] args) {
        try {
            Socket s = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("Verbunden mit " + SERVER_HOST + ":" + SERVER_PORT);
            System.out.println("Beende Chat mit 'Bye'\n");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream());
            
            Thread receiverThread = new Thread(new MessageReceiver(in));
            receiverThread.start();
            
            Thread senderThread = new Thread(new MessageSender(out, s));
            senderThread.start();
            
            senderThread.join();
            receiverThread.join();
            
            s.close();
            System.out.println("\nVerbindung geschlossen.");
            
        } catch (IOException e) {
            System.err.println("Verbindungsfehler: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Thread unterbrochen: " + e.getMessage());
        }
    }
}
