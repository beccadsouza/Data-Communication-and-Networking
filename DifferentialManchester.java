package DCCN;
import java.util.Scanner;
/*
* Created by Rebecca D'souza on 10/08/18
* */
public class DifferentialManchester {
    public static void main(String args[]){
        String data[] = {"01", "10"};
        System.out.print("Enter the signal bits : ");
        Scanner sc = new Scanner(System.in);
        String code = sc.next();
        for(int start = 0;start <2;start++) {
            System.out.println("\nAssuming starting voltage to be " + (start==0?"low":"high") + " voltage");
            StringBuilder ans = new StringBuilder(String.valueOf(start));
            if (code.charAt(0) == '1') ans.append(data[start]);
            else ans.append(data[(start + 1) % 2]);
            for (char x : code.substring(1).toCharArray()) {
                StringBuilder temp = new StringBuilder(ans.substring(ans.length() - 2));
                if (x == '1') ans.append(temp.reverse());
                else ans.append(temp);
            }
            StringBuilder decode = new StringBuilder("");
            System.out.println("Encoded signals are : "+ans.substring(1).replaceAll("0","-1"));
            System.out.print("Decoded signals are : ");
            if(ans.charAt(0)==ans.charAt(1)) decode.append("1");
            else decode.append("0");
            for(int i = 3;i<ans.length()-1;i+=2)
                if(ans.substring(i - 2, i).equals(ans.substring(i, i + 2)))decode.append("0");
                else decode.append("1");
            System.out.println(decode);
        }
    }
}


/*              OUTPUT                  */
/*
Enter the signal bits : 0100

Assuming starting voltage to be low voltage
Encoded signals are : 1-1-11-11-11
Decoded signals are : 0100

Assuming starting voltage to be high voltage
Encoded signals are : -111-11-11-1
Decoded signals are : 0100

Process finished with exit code 0
* */
/*
Enter the signal bits : 10011100

Assuming starting voltage to be low voltage
Encoded signals are : -11-11-111-1-111-11-11-1
Decoded signals are : 10011100

Assuming starting voltage to be high voltage
Encoded signals are : 1-11-11-1-111-1-11-11-11
Decoded signals are : 10011100

Process finished with exit code 0
* */