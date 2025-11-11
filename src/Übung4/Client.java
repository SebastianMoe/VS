package Übung4;

import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Client {
    public static void main(String[] args) {
        try {
            Socket  socket = new Socket("localhost", 8080);
            Scanner scanner = new Scanner(System.in);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output);

            System.out.print("Geben sie ihren Usernamen ein: ");
            String username = scanner.nextLine();

            writer.println("REG" + username);
            writer.flush();

            String response = reader.readLine();
            System.out.println(response);

            String message = "";
            System.out.println("Geben sie ihren Befehl ein: send <Empfänger> <Message>, getMessages oder exit");
            message = scanner.nextLine();

            if (message.startsWith("send ")) {
                String[] parts = message.split(" ", 3);
                if (parts.length < 3) {
                    System.out.println("Ungültiger Befehl. Benutzen Sie: send <Empfänger> <Message>");
                }
                String recipient = parts[1];
                String msgContent = parts[2];
                message = "SND" + username + "#" + recipient + "#" + msgContent;
                writer.println(message);
                writer.flush();

            } else if (message.equals("getMessages")) {
                message = "RCV" + username;
                writer.println(message);
                writer.flush();

                response = reader.readLine();
                System.out.println(response);
            } else {
                System.out.println("Unbekannter Befehl.");
            }
            
            scanner.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}