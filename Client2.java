package DCCN;
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
            message = br.readLine();
            if(message.equals("bye")) break;
            System.out.println("Client 1 : "+message.substring(0,message.length()-1));

            if(message.charAt(message.length()-1)=='$') {
                System.out.println("Client 2 : Decoded frames data is ");
                String[] temp = message.substring(1, message.length() - 2).split("FF");
                for (String x : temp) {
                    String y = x.substring(1, x.length() - 1);
                    String ans = "";
                    for (int i = 0; i < y.length(); i++) {
                        if (i == 0 && !al.contains(y.charAt(i))) {
                            ans += y.charAt(i);
                        } else if (i != 0) {
                            if (al.contains(y.charAt(i))) {
                                if (y.charAt(i - 1) == ESC) {
                                    if (!(i != 1 && y.charAt(i - 2) == ESC && y.charAt(i) == ESC))
                                        ans += y.charAt(i);
                                }
                            } else ans += y.charAt(i);
                        }
                    }
                    System.out.println(ans);
                }
            }
            else {
                System.out.println("Client 2 : Decoded frames data is ");
                String[] temp = message.substring(8, message.length() - 9).split(flag+flag);
                for (String x : temp) {
                    x = x.replaceAll("111110","11111");
                    System.out.println(x);
                }
            }
        }
        System.out.println("Client 1 : bye");
        sock.close();
        br.close();
        pw.close();
    }
}