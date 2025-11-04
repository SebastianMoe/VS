package Übung3.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    private static final int MAX_LENGTH = 100;
    private static final int PORT = 8080;
    
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(PORT);
            System.out.println("UDP Receiver gestartet auf Port " + PORT);
            System.out.println("Maximale Nachrichtenlänge: " + MAX_LENGTH + " Bytes");
            System.out.println("Warte auf Nachrichten... (beende mit 'Ende')\n");
            
            byte[] buffer = new byte[MAX_LENGTH];
            
            while(true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                
                String message = new String(packet.getData(), 0, packet.getLength());
                String senderAddress = packet.getAddress().getHostAddress();
                int senderPort = packet.getPort();
                
                System.out.println("Von: " + senderAddress + ":" + senderPort);
                System.out.println("Nachricht: " + message);
                System.out.println("Länge: " + packet.getLength() + " Bytes");
                
                if (message.equals("Ende")) {
                    System.out.println("\n'Ende' empfangen - Receiver beendet.");
                    break;
                }
                
                System.out.println();
            }
            socket.close();
        } catch (IOException e) {
            System.err.println("Receiver-Fehler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
