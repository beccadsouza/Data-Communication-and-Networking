package DCCN.framing;
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
            message = "";
            System.out.println("Client 1 : ");
            System.out.println("Press 1 for byte stuffing\nPress 2 for bit stuffing\nPress 3 to exit");
            int opt = sc.nextInt();
            if(opt==3){
                pw.println("bye");
                pw.flush();
                break;
            }
            else if (opt==1) {
                System.out.print("Enter number of frames : ");
                int f = sc.nextInt();
                String arr[] = new String[f + 1];
                for (int i = 1; i < f + 1; i++) {
                    System.out.println("Enter frame " + i);
                    arr[i] = sc.next();
                    String temp = FLAG + "" + header;
                    for (char x : arr[i].toCharArray())
                        if (x == FLAG || x == header || x == trailer || x == ESC)
                            temp += ESC + "" + x;
                        else temp += x;
                    temp += trailer + "" + FLAG;
                    message += temp;
                }
                System.out.println("Encoded frames data sent : "+message+"\n");
                message += "$";
            }
            else{
                System.out.println("Enter number of frames : ");
                int f = sc.nextInt();
                String MSG="",temp;
                for (int i = 1; i < f + 1; i++) {
                    System.out.println("Enter frame " + i);
                    message =sc.next();
                    if(message.length()>5){
                        temp = message.substring(0, 5);
                        for (int j = 5; j < message.length(); j++){
                            if (message.substring(j - 5, j).equals("11111")) {
                                temp += "0";
                            }
                            temp += message.charAt(j);
                        }
                    }
                    else temp = message;
                    MSG += flag+temp+flag;
                }
                System.out.println("Encoded frames data sent : "+MSG+"\n");
                message = MSG+"@";
            }
            pw.println(message);
            pw.flush();
        }

        sock.close();
        br.close();
        pw.close();
    }
}   