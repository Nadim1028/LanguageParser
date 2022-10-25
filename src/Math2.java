public class Math2 {
    public static void main(String[] args) {
        int num1, num2;
        num1=1;
        num2=5;

        int num3 = printSum(num1,num2);
        System.out.println(num3);
    }
    public static int printSum(int num1, int num2){
        return num1+num2;
    }

    public float floatMethod(){
        return 5;
    }

    public boolean booleanMethod(){
        return true;
    }
    public String stringMethod(){
        return "5";
    }
    public double doubleMethod(){
        return 5;
    }

    static int myMethod(int x) {
        return 5 + x;
    }

    public int addTwoInt(int a, int b)
    {

        // Adding two integer value
        int sum = a + b;

        // Returning summation of two values
        return sum;
    }

    static void checkAge(int age) {

        if (age < 18) {
            System.out.println("Access denied - You are not old enough!");

        } else {
            System.out.println("Access granted - You are old enough!");
        }
        for(int i=0;i<10;i++){
            System.out.println(i);
        }

        int nint = (5*6);

    }

}
