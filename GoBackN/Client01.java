package DCCN.GoBackN;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Created by Rebecca D'souza on 02.09.18
 * */
public class Client01 {
    public static void main(String[] args) throws Exception {
        Socket sock = new Socket("127.0.0.1", 3000);
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        int windowSize = 4;
        int count = 0,position = 0;
        String clown = "";

        ArrayList<String> al = new ArrayList<>();
        while(true) {
            System.out.println("Enter number of packages");
            int n = sc.nextInt();
            if(n==0) {
                pw.println("bye");
                pw.flush();
                break;
            }
            for(int i = 1;i<n+1;i++){
                count++;
                System.out.println("Sending package "+i+" count "+count);
                pw.println("package "+i);
                pw.flush();

                sock.setSoTimeout(10000);
                try {
                    String temp = br.readLine();
                    if(!temp.equals(null))al.add(temp);
                }catch (Exception ignored){ }

                if(count==windowSize) {
                    if (al.size() != 0) {
                        clown = al.get(al.size() - 1);
                        System.out.println(clown);
                        if (clown.substring(0, 3).equals("nak")) {
                            position = Integer.parseInt(clown.substring(3));
                            System.out.println("Nak recieved for package " + position);
                        } else {
                            position = Integer.parseInt(clown.substring(3)) + 1;
                            System.out.println("Ack last recieved for package " + (position - 1));
                        }
                        i = position - 1;
                        System.out.println("Sending from package " + (i + 1));
                        count = 0;
                        al = new ArrayList<>();
                    } else if (al.size() == 0) {
                        System.out.println("All packages reached safely. WTF");
                    }
                }
            }
        }

        sock.close();
        br.close();
        pw.close();
    }
}   
