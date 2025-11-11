package Übung4;

import java.util.concurrent.ExecutorService;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 8080;
        MessageStore messageStore = new MessageStore();
        UserStore userStore = new UserStore();
        ExecutorService threadPool = java.util.concurrent.Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(new ClientRequest(clientSocket, messageStore, userStore));
            }

        } catch (java.io.IOException e) {
            System.err.println("Server-Fehler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
