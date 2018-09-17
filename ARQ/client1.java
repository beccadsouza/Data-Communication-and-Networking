package DCCN.ARQ;
import java.io.*;
import java.net.*;
import java.util.*;
/*
 * Created by Rebecca D'souza on 02.09.18
 * */
public class client1 {
    public static void main(String[] args) throws Exception {
        Socket sock = new Socket("127.0.0.1", 3000);
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        System.out.println("Press 1 for Stop and Wait ARQ\nPress 2 for Go Back N\nPress 3 to exit");
        int option = sc.nextInt();
        pw.println(option);
        pw.flush();
        while(option!=3) {
            if (option == 1) {
                String ack;
                Long start, end;
                while (true) {
                    System.out.println("Enter number of packages");
                    int n = sc.nextInt();
                    if (n == 0) {
                        pw.println("bye");
                        pw.flush();
                        break;
                    }
                    for (int i = 1; i < n + 1; i++) {
                        pw.println("package " + i);
                        pw.flush();
                        ack = "";

                        start = System.nanoTime();
                        sock.setSoTimeout(10000);
                        try {
                            ack = br.readLine();
                        } catch (Exception ignored) {
                        }
                        end = System.nanoTime();

                        if (ack.equals("ack" + i)) {
                            for (int j = 0; j < (int) ((end - start) / 1000000000); j += 2)
                                System.out.println(j + "s");
                            System.out.println("Data " + i + " received in time : " + ack);
                        } else {
                            for (int j = 0; j < 11; j += 2)
                                System.out.println(j + "s");
                            System.out.println("Data " + i + " not received in time");
                            --i;
                        }
                    }
                }
            } else {
                int windowSize = 4;
                int count = 0, position;
                String clown;
                ArrayList<String> al = new ArrayList<>();
                while (true) {
                    System.out.println("Enter number of packages");
                    int n = sc.nextInt();
                    if (n == 0) {
                        pw.println("bye");
                        pw.flush();
                        break;
                    }
                    for (int i = 1; i < n + 1; i++) {
                        count++;
                        System.out.println("Sending package " + i + " window " + count);
                        pw.println("package " + i);
                        pw.flush();

                        sock.setSoTimeout(10000);
                        try {
                            String temp = br.readLine();
                            if (!temp.equals(null)) al.add(temp);
                        } catch (Exception ignored) {
                        }

                        if (count == windowSize || i==n) {
                            if (al.size() != 0) {
                                clown = al.get(al.size() - 1);
                                System.out.println(clown);
                                if (clown.substring(0, 3).equals("nak")) {
                                    position = Integer.parseInt(clown.substring(3));
                                    System.out.println("Nak received for package " + position);
                                    al = new ArrayList<>();
                                } else {
                                    position = Integer.parseInt(clown.substring(3)) + 1;
                                    System.out.println("Ack last received for package " + (position - 1));
                                }
                                i = position - 1;
                                if(i+1<=n)
                                    System.out.println("Sending from package " + (i + 1));
                                count = 0;
                            } else if (al.size() == 0) {
                                System.out.println("All packages reached safely. WTF");
                            }
                        }
                    }
                }
            }
            System.out.println("Press 1 for Stop and Wait ARQ\nPress 2 for Go Back N\nPress 3 to exit");
            option = sc.nextInt();
            pw.println(option);
            pw.flush();
        }
        sock.close();
        br.close();
        pw.close();
    }
}
