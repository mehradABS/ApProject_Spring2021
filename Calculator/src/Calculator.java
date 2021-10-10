import java.util.Scanner;

public class Calculator {

    public static String getCommand(String command, int chatId) {
        try {
            return String.valueOf(AllActions(new StringBuilder(command)));
        }
        catch (Exception ignored){
        }
        return null;
    }

    static int findingQuestion(StringBuilder s,int i){
        i++;
        while(i<s.length()){
            if(s.charAt(i)!='?'){
                break;
            }
            i++;
        }
        return i-1;
    }
    //i==index of question

    static int findingNumber(StringBuilder s,int leftDigit){
        leftDigit++;
        while(leftDigit<s.length()){
            if(!(s.charAt(leftDigit)-'0'>-1 && s.charAt(leftDigit)-'0'<10)){
                break;
            }
            leftDigit++;
        }
        return leftDigit-1;
    }

    static int findLessTilde(StringBuilder s,int i){
        i--;
        while(i>=0){
            if(s.charAt(i)!='<' && s.charAt(i)!='~'){
                break;
            }
            i--;
        }
        return i+1;
    }

    static int findNumber(StringBuilder s,int lastDigit){
        lastDigit--;
        while(lastDigit>=0){
            if(!(s.charAt(lastDigit)-'0'>-1 && s.charAt(lastDigit)-'0'<10)){
                break;
            }
            lastDigit--;
        }
        return lastDigit+1;
    }

    static int tilde(long n){
        int sum=0;
        int i=0;
        while(n>0){
            sum= (int) (sum+(n%10));
            i++;
            n/=10;
        }
        return sum/i;
    }

    static long dollar(long a,long b){
        StringBuilder a0= new StringBuilder(String.valueOf(a));
        StringBuilder b0= new StringBuilder(String.valueOf(b));
        if(a0.length()>b0.length()) {
            int length=b0.length();
            for (int i = 0; i < a0.length() - length; i++) {
                b0.insert(0, '0');
            }
        }
        if(a0.length()<b0.length()){
            int length=a0.length();
            for (int i = 0; i < b0.length()-length; i++) {
                a0.insert(0, '0');
            }
        }
        StringBuilder c=new StringBuilder();
        for (int i = 0; i < a0.length(); i++) {
            c.append(((a0.charAt(i) - '0') + (b0.charAt(i) - '0')) / 2);
        }
        return Long.parseLong(c.toString());
    }

    static long Hashtag(long a,long b){
        return  (2*a+(long)Math.pow(b,2));
    }

    static boolean isPrime(long a){
        if(a<=1){
            return false;
        }
        for (int i = 2; i <=Math.sqrt(a) ; i++) {
            if(a%i==0){
                return false;
            }
        }
        return true;
    }

    static long findingPrimeNumber(long a){
        if(a<=3){
            return 2;
        }
        for (int i = (int) (a-1); i >=3 ; i--) {
            if(isPrime(i)){
                return i;
            }
        }
        return 2;
    }

    static long question(long a) {
        int sum = 0;
        while (a > 0) {
            int m = 1;
            int b = (int) a % 10;
            if (b != 0) {
                for (int i = (int) b; i > 1; i--) {
                    m *= i;
                }
            }
            sum += m;
            a /= 10;
        }
        return sum;
    }

    static String findingNeighborParenthesis(StringBuilder s){
        int first=-1;
        int last=-1;
        char c=' ';
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i)=='('){
                first=i;
                c='(';
            }
            else if(s.charAt(i)==')'){
                if(c=='('){
                    last=i;
                    break;
                }
                c=')';
            }
        }
        return first+" "+last;
    }

    static void doingAction(StringBuilder s){
        for (int i = 1; i < s.length(); i++) {
            if(s.charAt(i)=='?'){
                int qu=findingQuestion(s,i);
                int i0=findNumber(s,i-1);
                long number=Long.parseLong(s.substring(i0,i));
                for (int j = 0; j < qu-i+1; j++) {
                    number=question(number);
                }
                s.delete(i0,qu+1);
                s.insert(i0,number);
                i=i0;
            }
        }
        for (int i = s.length()-1; i >=0 ; i--) {
            if(s.charAt(i)=='<' || s.charAt(i)=='~'){
                int qu=findLessTilde(s,i);
                int i0=findingNumber(s,i+1);
                long number=Long.parseLong(s.substring(i+1,i0+1));
                for (int j = i; j >= qu; j--) {
                    if(s.charAt(j)=='<'){
                        number=findingPrimeNumber(number);
                    }
                    else{
                        number=tilde(number);
                    }
                }
                s.delete(qu,i0+1);
                s.insert(qu,number);
                i=qu;
            }
        }
        for (int i = s.length()-1; i >= 0; i--) {
            if(s.charAt(i)=='$'){
                int b0=findingNumber(s,i+1);
                int a0=findNumber(s,i-1);
                long b=Long.parseLong(s.substring(i+1,b0+1));
                long a=Long.parseLong(s.substring(a0,i));
                s.delete(a0,b0+1);
                s.insert(a0,dollar(a,b));
                i=s.length();
            }
        }
        for (int i = s.length()-1; i >= 0; i--) {
            if(s.charAt(i)=='*'){
                int b0=findingNumber(s,i+1);
                int a0=findNumber(s,i-1);
                long b=Long.parseLong(s.substring(i+1,b0+1));
                long a=Long.parseLong(s.substring(a0,i));
                s.delete(a0,b0+1);
                s.insert(a0,(a*b));
                i=s.length();
            }
        }
        for (int i = s.length()-1; i >= 0; i--) {
            if(s.charAt(i)=='#'){
                int b0=findingNumber(s,i+1);
                int a0=findNumber(s,i-1);
                long b=Long.parseLong(s.substring(i+1,b0+1));
                long a=Long.parseLong(s.substring(a0,i));
                s.delete(a0,b0+1);
                s.insert(a0,Hashtag(a,b));
                i=s.length();
            }
        }
        for (int i = s.length()-1; i >= 0; i--) {
            if(s.charAt(i)=='+'){
                int b0=findingNumber(s,i+1);
                int a0=findNumber(s,i-1);
                long b=Long.parseLong(s.substring(i+1,b0+1));
                long a=Long.parseLong(s.substring(a0,i));
                s.delete(a0,b0+1);
                s.insert(a0,a+b);
                i=s.length();
            }
        }
    }

    static long AllActions(StringBuilder s) {
        while (true) {
            Scanner khar = new Scanner(findingNeighborParenthesis(s));
            int a = khar.nextInt();
            int b = khar.nextInt();
            if (a != -1 && b != -1) {
                StringBuilder s1 = new StringBuilder(s.substring(a + 1, b));
                doingAction(s1);
                s.delete(a,b+1);
                s.insert(a,s1.toString());
            }
            else{
                break;
            }
        }
        doingAction(s);
        return Long.parseLong(s.toString());
    }

}