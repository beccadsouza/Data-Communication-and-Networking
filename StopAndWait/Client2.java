package DCCN.StopAndWait;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * Created by Rebecca D'souza on 02.09.18
 * */
public class Client2 {
    public static void main(String[] args) throws Exception {
        Socket sock = new Socket("127.0.0.1", 2000);
        PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        while(true) {
        	String received ;
        	if(br.ready()){
        		received = br.readLine();
            	if(received.equals("bye")) break;
            	System.out.println("Received data "+received+"\n");
            }
        }
        sock.close();
        br.close();
        pw.close();
    }
}
