package Übung3.Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    private static final int SERVER_PORT = 1212;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            
            System.out.println("Warte auf Verbindungen...");


            /*
             * 
             new Thread(() -> {
                 try {
                     Thread.sleep(6000);
                     System.out.println("Server wird heruntergefahren...");
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }).start();
             */

            while(true) {
                try {
                Socket s = serverSocket.accept();
                System.out.println("Neue Verbindung von " + s.getInetAddress().getHostAddress() + ":" + s.getPort());
                
                boolean running = true;
                InputStream in = s.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                
                OutputStream out = s.getOutputStream();
                PrintWriter writer = new PrintWriter(out);
                
                writer.println("Please tell me your name!");
                writer.flush();

                String message = reader.readLine();
                writer.println("Hello " + message + "! You can start chatting now. Type 'Bye' to exit.");
                writer.flush();

                while (running) {

                    message = reader.readLine();
                    System.out.println(message);
                    
                    if (message.equalsIgnoreCase("Bye")) {
                        running = false;
                        s.close();
                        continue;
                    }

                    writer.println("*" + message + "*");
                    writer.flush();
                    
                    System.out.println("Verbindung geschlossen.\n");
                }
                //s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        } catch (IOException e) {
            System.err.println("Server-Fehler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
