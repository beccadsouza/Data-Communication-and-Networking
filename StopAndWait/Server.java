package DCCN.StopAndWait;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/*
* Created by Rebecca D'souza on 02.09.18
* */
public class Server {
    public static void main(String[] args) throws Exception {
        String message,prev="";
        System.out.println("Server is up\n");
        ServerSocket serverSocket1 = new ServerSocket(3000);
        Socket sock1 = serverSocket1.accept( );
        ServerSocket serverSocket2 = new ServerSocket(2000);
        Socket sock2 = serverSocket2.accept( );
        PrintWriter pw1 = new PrintWriter(sock1.getOutputStream(), true);
        PrintWriter pw2 = new PrintWriter(sock2.getOutputStream(), true);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(sock1.getInputStream()));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(sock2.getInputStream()));
        Random random = new Random();
        int delay;

        do {
            message = br1.readLine();
            if(message.equals("bye")){
            	System.out.println("Client 1 said bye");
            	pw2.println("bye");
            	pw2.flush();
            	break;
            }
            System.out.println("Sending data "+message+" to client 2");
            pw2.println(message);
            pw2.flush();
            if(prev.equals(message)) delay = 0;
            else delay = (random.nextInt(10)+5);
            System.out.println("Sending "+"ack" + message.charAt(message.length()-1)+" after "+delay+" seconds\n");
            try { Thread.sleep(delay*1000); }catch (Exception ignored){ }
            pw1.println("ack" + message.charAt(message.length()-1));
            pw1.flush();
            prev = message;
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
