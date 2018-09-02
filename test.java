package DCCN;

public class test {
    public static void main(String args[]){
        String string1 = "ABC";
        String binary = "";
        String string2 = "";
        for(char x : string1.toCharArray()){
            String temp = Integer.toBinaryString((int)x);
            binary += ("00000000"+temp).substring(temp.length());
        }
        System.out.println(binary);
        for(int i = 0;i<binary.length();i+=8){
            char t = (char)Integer.parseInt(binary.substring(i,i+8),2);
            string2 += ""+t;
        }
        System.out.println(string2);
    }
}
