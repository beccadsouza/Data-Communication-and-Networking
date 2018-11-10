package DCCN.Link_State_Routing;
import java.util.Scanner;

/*
* Created by Rebecca D'souza on 15.10.18
* */

public class Main{
    public static Scanner sc = new Scanner(System.in);
    private static void classify(){
        System.out.println("Enter number of address");
        int number = sc.nextInt();
        while(number--!=0){
            System.out.print("Enter IP Address : ");
            String address = sc.next();
            int addressClass = Integer.parseInt(address.substring(0,address.indexOf(".")));
            if (addressClass<127&&addressClass>0)System.out.println("IP address is of class A\n");
            if (addressClass<192&&addressClass>127)System.out.println("IP address is of class B\n");
            if (addressClass<224&&addressClass>191)System.out.println("IP address is of class C\n");
            if (addressClass<240&&addressClass>223)System.out.println("IP address is of class D\n");
            if (addressClass<255&&addressClass>239)System.out.println("IP address is of class E\n");
        }
    }
    private static void routing(){
        int n, u, v, w, e, min, node = 0;
        System.out.print("Enter number of nodes and number of connections : ");
        n = sc.nextInt();
        e = sc.nextInt();
        int adj[][] = new int[n][n];
        int visited[] = new int[n];
        int preD[] = new int[n];
        int distance[] = new int[n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) adj[i][j] = 10000;
        System.out.println("Enter the node connections : ");
        for (int i = 0; i < e; i++) {
            u = sc.nextInt();
            v = sc.nextInt();
            w = sc.nextInt();
            if (u != v) adj[u-1][v-1] = adj[v-1][u-1] = w;
        }
        System.out.print("\nAdjacency matrix of node connections : ");
        for (int i = 0; i < n; i++) {
            System.out.println();
            for (int j = 0; j < n; j++) System.out.print(adj[i][j] != 10000 ? adj[i][j] + "     " : "OO    ");
        }
        for (int src = 0; src < n; src++) {
            for (int i = 0; i < n; i++) {
                visited[i] = 0;
                preD[i] = src;
            }
            System.arraycopy(adj[src], 0, distance, 0, n);
            visited[src] = 1;
            distance[src] = 0;
            for (int i = 0; i < n; i++) {
                min = 10000;
                for (int j = 0; j < n; j++)
                    if (visited[j] != 1 && min > distance[j]) {
                        min = distance[j];
                        node = j;
                    }
                visited[node] = 1;
                for (int k = 0; k < n; k++) {
                    if (min + adj[node][k] < distance[k] && visited[k] != 1) {
                        distance[k] = min + adj[node][k];
                        preD[k] = node;
                    }
                }
            }
            System.out.println("\n\nRouting table for node " + (src + 1) + " : ");
            System.out.printf("%s\t%s\t%s", "src ", "dest", "via");
            for (int i = 0; i < n; i++) {
                if (i != src) {
                    boolean direct = true;
                    System.out.printf("\n%d\t\t%d\t\t", src + 1, i + 1);
                    int temp = i;
                    while (temp != src) {
                        if (preD[temp] != src) {
                            System.out.printf("%d, ", preD[temp] + 1);
                            direct = false;
                        }
                        temp = preD[temp];
                    }
                    if (direct) System.out.print("-");
                }
            }
        }
        System.out.println("\n");
    }
    public static void main(String args[]) {
        System.out.println("Press 1 for IP Address Class identification\nPress 2 for Intra-domain Routing\nPress 3 to exit");
        int opt = sc.nextInt();
        while (opt!=3){
            if(opt==1) classify();
            else routing();
            System.out.println("Press 1 for IP Address Class identification\nPress 2 for Intra-domain Routing\nPress 3 to exit");
            opt = sc.nextInt();
        }
    }

}

/*
240.10.42.2
10.10.10.10
192.168.19.46
225.0.58.22
172.16.5.73
* */

/*
2 1 2
3 1 5
3 2 2
4 1 8
4 3 4
5 3 1
5 4 7
6 2 6
6 5 5
* */