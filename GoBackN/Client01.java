package DCCN.GoBackN;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
        String ack = "";
        Long start,end;
        while(true) {
            System.out.println("Enter number of packages");
            int n = sc.nextInt();
            if(n==0) {
                pw.println("bye");
                pw.flush();
                break;
            }
            for(int i = 1;i<n+1;i++){
                pw.println("package "+i);
                pw.flush();
                ack = "";

                start = System.nanoTime();
                sock.setSoTimeout(10000);
                try { ack = br.readLine(); }catch (Exception ignored){ }
                end = System.nanoTime();

                if(ack.equals("ack"+i)){
                    for(int j=1;j<(int)((end-start)/1000000000);j++)
                        System.out.println(j+"s");
                    System.out.println("Data "+i+" received in time : "+ack);
                }
                else{
                    for(int j=1;j<11;j++)
                        System.out.println(j+"s");
                    System.out.println("Data "+i+" not received in time");
                    --i;
                }
            }
        }

        sock.close();
        br.close();
        pw.close();
    }
}   
