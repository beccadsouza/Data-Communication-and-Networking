package DCCN;
import java.io.*;
import java.net.*;

public class Client1 {
    public static void main(String[] args) throws Exception {
        String message;

        Socket sock = new Socket("127.0.0.1", 3000);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        while(true) {
            System.out.print("Client 1 : ");
            message = br1.readLine();
            pw.println(message);
            pw.flush();
            if(message.equals("bye")) break;

            message = br2.readLine();
            System.out.println("Client 2 : "+message);
            if(message.equals("bye")) break;
        }

        sock.close();
        br1.close();
        br2.close();
        pw.close();
    }
}   