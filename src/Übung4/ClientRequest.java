package Übung4;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class ClientRequest implements Runnable {
    private Socket clientSocket;
    private MessageStore messageStore;
    private UserStore userStore;

    public ClientRequest(Socket clientSocket, MessageStore messageStore, UserStore userStore) {
        this.clientSocket = clientSocket;
        this.messageStore = messageStore;
        this.userStore = userStore;
    }

    @Override
    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(output);

            String inputString = reader.readLine();
            String function = inputString.substring(0, 3);

            switch (function) {
                case "REG":
                    if (userStore.getUser(inputString.substring(3)) != null) {
                        writer.println("Willkommen zurück " + inputString.substring(3));
                        writer.flush();
                        break;
                    }
                    userStore.addUser(inputString.substring(3));
                    writer.println("Benutzer " + inputString.substring(3) + " registriert.");
                    writer.flush();
                    break;

                case "SND":
                    String message = inputString.substring(3);
                    String[] parts = message.split("#");
                    User sender = userStore.getUser(parts[0]);
                    User receiver = userStore.getUser(parts[1]);

                    if (sender == null || receiver == null) {
                        writer.println("ERROR: Unbekannter Benutzer");
                        writer.flush();
                        break;
                    }

                    messageStore.addMessage(new Message(parts[2], sender, receiver));
                    //clientSocket.close();
                    break;

                case "RCV":
                    System.out.println("test");
                    User receiver2 = userStore.getUser(inputString.substring(3));

                    if (receiver2 == null) {
                        writer.println("ERROR: Unbekannter Benutzer");
                        writer.flush();
                        break;
                    }

                    List<Message> messages = messageStore.getMessages(receiver2);
                    System.out.println("test: " + messages.size());
                    for (Message msg : messages) {
                        writer.println("FROM: " + msg.getSender().getUsername() + " MSG: " + msg.getContent());
                    }
                    writer.flush();
                    break;
            
                default:
                    break;
            }
        } catch (Exception e) {
            //handle exception
        } finally {
            try {
                //clientSocket.close();
            } catch (Exception e) {
                //handle exception
            }
        }
    }
}
