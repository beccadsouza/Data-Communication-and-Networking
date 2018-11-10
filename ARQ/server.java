package DCCN.ARQ;
import java.io.*;
import java.net.*;
import java.util.*;
/*
 * Created by Rebecca D'souza on 04.09.18
 * */
public class server {
    public static void main(String[] args) throws Exception {
        System.out.println("Server is up\n");
        ServerSocket serverSocket1 = new ServerSocket(3000);
        Socket sock1 = serverSocket1.accept();
        ServerSocket serverSocket2 = new ServerSocket(2000);
        Socket sock2 = serverSocket2.accept();
        PrintWriter pw1 = new PrintWriter(sock1.getOutputStream(), true);
        PrintWriter pw2 = new PrintWriter(sock2.getOutputStream(), true);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(sock1.getInputStream()));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(sock2.getInputStream()));
        Random random = new Random();
        String option = br1.readLine();
        while (!option.equals("3")){
            if (option.equals("1")) {
                int delay;
                String message, prev = "";
                do {
                    message = br1.readLine();
                    if (message.equals("bye")) {
                        System.out.println("Transfer of packages by Stop and Wait completed\n");
                        pw2.println("bye");
                        pw2.flush();
                        break;
                    }
                    System.out.println("Sending data " + message + " to client 2");
                    pw2.println(message);
                    pw2.flush();
                    if (prev.equals(message)) delay = 0;
                    else delay = (random.nextInt(10) + 5);
                    System.out.println("Sending " + "ack" + message.substring(8) + " after " + delay + " seconds");
                    try {
                        Thread.sleep(delay * 1000);
                    } catch (Exception ignored) { }
                    pw1.println("ack" + message.substring(8));
                    pw1.flush();
                    prev = message;
                } while (!message.equals("bye"));
            } else {
                String message;
                boolean funny = true;
                int curr = 1;

                do {
                    message = br1.readLine();
                    if (message.equals("bye")) {
                        System.out.println("Transfer of packages by Go Back N completed\n");
                        pw2.println("bye");
                        pw2.flush();
                        break;
                    }
                    if (curr >= Integer.parseInt(message.substring(8))) {
                        funny = true;
                    }
                    pw2.println(message);
                    pw2.flush();
                    if (random.nextInt(100) % 4 == 0 && funny && curr != 1) {
                        funny = false;
                        if (random.nextInt(20) % 2 == 0) {
                            System.out.println("Sending " + "nak" + Integer.parseInt(message.substring(8)));
                            pw1.println("nak" + Integer.parseInt(message.substring(8)));
                            pw1.flush();
                        } else {
                            System.out.println("Not sending acks anymore");
                        }
                    }
                    if (funny) {
                        System.out.println("Sending " + "ack" + Integer.parseInt(message.substring(8)));
                        pw1.println("ack" + Integer.parseInt(message.substring(8)));
                        pw1.flush();
                    }
                    curr = Integer.parseInt(message.substring(8));
                } while (!message.equals("bye"));
            }
            option = br1.readLine();
        }
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
