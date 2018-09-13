package DCCN.arq;

import java.io.*;
import java.net.*;
import java.util.*;
/*
 * Created by Rebecca D'souza on 02.09.18
 * */
public class Client2 {
    public static void main(String[] args) throws Exception {
        String message;
        char FLAG = 'F';
        char header = 'H';
        char trailer = 'T';
        char ESC = 'E';
        String flag = "01111110";
        Socket sock = new Socket("127.0.0.1", 2000);
        PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        List<Character> al = Arrays.asList(FLAG,header,trailer,ESC);
        while(true) {
        	String received = "";
        	if(br.ready()){
        		received = br.readLine();
            	if(received.equals("bye")) break;
            	System.out.println("Received data "+received);
            }
        }
        sock.close();
        br.close();
        pw.close();
    }
}
