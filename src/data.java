import java.util.Scanner;

public class data
{
    public static void main(String args[])
    {
        float a,b,c;
        int n1;
        int n2;
        int n3;
        String str = "a",str2;

        Scanner in = new Scanner(System.in);
        a = in.nextInt();
        b = in.nextInt();
        c = in.nextInt();
        if((a>b)&&(a>c))
        {
            System.out.println("A is Greater"+a);
        }

        else if ( b > a && b > c )
            System.out.println("B is largest."+b);
        else
            System.out.println("C is larger");
    }
}