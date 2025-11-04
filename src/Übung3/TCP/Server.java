package Übung3.TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Warte auf Verbindungen...");
            
            Socket s = serverSocket.accept();
            System.out.println("Neue Verbindung von " + s.getInetAddress().getHostAddress() + ":" + s.getPort());

            InputStream in = s.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            
            OutputStream out = s.getOutputStream();
            PrintWriter writer = new PrintWriter(out);

            while(true) {
                try {
                    String message = reader.readLine();
                    System.out.println(message);
                    
                    writer.println("*" + message + "*");
                    writer.flush();
                    
                    s.close();
                    System.out.println("Verbindung geschlossen.\n");
                    
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
