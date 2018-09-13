package DCCN.framing;
import java.io.*;
import java.net.*;
/*
* Created by Rebecca D'souza on 02.09.18
* */
public class Server {
    public static void main(String[] args) throws Exception {
        String message;
        System.out.println("Server is up");
        ServerSocket serverSocket1 = new ServerSocket(3000);
        Socket sock1 = serverSocket1.accept( );
        ServerSocket serverSocket2 = new ServerSocket(2000);
        Socket sock2 = serverSocket2.accept( );
        PrintWriter pw1 = new PrintWriter(sock1.getOutputStream(), true);
        PrintWriter pw2 = new PrintWriter(sock2.getOutputStream(), true);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(sock1.getInputStream()));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(sock2.getInputStream()));

        do {
            message = br1.readLine();
            pw2.println(message);
            pw2.flush();
        } while (!message.equals("bye"));

        serverSocket1.close();
        serverSocket2.close();
        sock1.close();
        sock2.close();
        pw1.close();
        pw2.close();
        br1.close();
        br2.close();
        System.out.println("Server shutting down.");
    }
}