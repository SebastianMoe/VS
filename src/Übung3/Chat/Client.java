package Übung3.Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 1212;
    
    public static void main(String[] args) {
        try {
            Socket s = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("Verbunden mit " + SERVER_HOST + ":" + SERVER_PORT);
            System.out.println("Beende Chat mit 'Bye'\n");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream());

            Scanner scanner = new Scanner(System.in);
            
            new Thread(() -> {
                while (true) {
                    String message = scanner.nextLine();
                
                    out.println(message);
                    if (message.equalsIgnoreCase("Bye")) {
                            out.flush();
                            try {
                                s.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Thread.currentThread().interrupt();
                        }
                    out.flush();
                }
            }).start();

            new Thread(() -> {
                while (s.isConnected()) {
                    String message;
                    try {
                        message = in.readLine();
                        System.out.println(message);
                    } catch (IOException e) {
                        System.out.println("Fehler beim Empfangen der Nachricht: " + e.getMessage());
                    }
                }
            }).start();

            scanner.close();
            System.out.println("\nVerbindung geschlossen.");
            
        } catch (IOException e) {
            System.err.println("Verbindungsfehler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
