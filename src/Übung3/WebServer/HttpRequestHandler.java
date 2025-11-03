package Übung3.WebServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HttpRequestHandler implements Runnable {
    private Socket clientSocket;
    
    public HttpRequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            String requestLine = in.readLine();
            
            if (requestLine == null || requestLine.isEmpty()) {
                return;
            }
            
            System.out.println("[" + Thread.currentThread().getName() + "] Request: " + requestLine);
            
            String[] parts = requestLine.split(" ");

            if (parts[0].equals("GET")) {
                String requestedPath = parts[1];
            
                Path filePath = Paths.get("./src/Übung3/WebServer" + requestedPath);

                List<String> lines = Files.readAllLines(filePath);
                String content = String.join("\n", lines);

                out.print( parts[2] + "\r\n");
                out.print("Content-Type: text/html\r\n");
                out.print("Content-Length: " + content.length() + "\r\n");
                out.print("\r\n");
                out.print(content);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Fehler bei Anfrage: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
