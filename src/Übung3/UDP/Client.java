package Übung3.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    private static final int MAX_LENGTH = 100; 
    private static final int SERVER_PORT = 8080;
    
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);
            System.out.println("UDP Sender gestartet");
            System.out.println("Maximale Nachrichtenlänge: " + MAX_LENGTH + " Bytes");
            System.out.println("Sende 'Ende' um den Receiver zu beenden\n");
            
            InetAddress serverAddress = InetAddress.getByName("localhost");
            
            while (true) {
                System.out.print("Nachricht: ");
                String message = scanner.nextLine();
                
                byte[] messageBytes = message.getBytes();
                if (messageBytes.length > MAX_LENGTH) {
                    System.out.println("FEHLER: Nachricht zu lang! (" + messageBytes.length + " Bytes, max. " + MAX_LENGTH + " Bytes)");
                    continue;
                }
                
                DatagramPacket packet = new DatagramPacket(
                    messageBytes,
                    messageBytes.length,
                    serverAddress,
                    SERVER_PORT
                );
                socket.send(packet);
                
                System.out.println("Gesendet (" + messageBytes.length + " Bytes)\n");
                
                if (message.equals("Ende")) {
                    System.out.println("Sender beendet");
                    break;
                }
            }
            socket.close();
            scanner.close();
        } catch (IOException e) {
            System.err.println("Sender-Fehler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
