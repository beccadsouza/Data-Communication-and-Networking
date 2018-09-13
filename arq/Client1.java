package DCCN.arq;

import java.io.*;
import java.net.*;
import java.util.*;
/*
 * Created by Rebecca D'souza on 02.09.18
 * */
public class Client1 {
    public static void main(String[] args) throws Exception {
        String message;
        char FLAG = 'F';
        char header = 'H';
        char trailer = 'T';
        char ESC = 'E';
        String flag = "01111110";
        Socket sock = new Socket("127.0.0.1", 3000);
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        while(true) {
            System.out.println("Enter number of packages");
            int n = sc.nextInt();
            if(n==0) {
            	pw.println("bye");
            	pw.flush();
            	break;
            }
            for(int i = 1;i<n+1;i++){
            	int j = 0;
            	pw.println("package "+i);
            	pw.flush();
            	while(!br.ready() && j!=30){
            		j++;
            		System.out.println(j+" s");
            	}
            	if(br.ready() && br.readLine().equals("ack"+i)){
		        	System.out.println(br.readLine());
		        	System.out.println("Data "+i+"received in time");
            	}
            	else{
            		System.out.println("Data"+i+" not received in time");	
            	}
            }
        }

        sock.close();
        br.close();
        pw.close();
    }
}   
