package Übung3.TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 8080);
            InputStream in = s.getInputStream();
            BufferedReader reader = new BufferedReader( new InputStreamReader(in) );

            OutputStream out = s.getOutputStream();
            PrintWriter writer = new PrintWriter(out);
            
            Scanner scanner = new Scanner(System.in);
            String eingabe = scanner.nextLine();
            
            writer.println(eingabe);
            writer.flush();
            
            String eingang = reader.readLine();
            System.out.println("Antwort vom Server:" + eingang);

            scanner.close();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
