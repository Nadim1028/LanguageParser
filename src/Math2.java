public class Math2 {
    public static void main(String[] args) {
        int num1, num2;
        num1=1;
        num2=5;
        int num3 = (num1*num2)/2;
        Math2 math2 = new Math2();
        math2.printSum(num1,num2);
        System.out.println(num2);

    }
    public  int printSum(int num1, int num2){
        booleanMethod();
        return num1+num2;
    }

    public float floatMethod(    ){
        booleanMethod();
        floatMethod();
        System.out.println("aaaa"+9);
        int num ;

        if(2>3){

        }
        for(int i=0;i<20;i++){

        }

        checkAge(5);
        return 5;
    }

    public boolean booleanMethod(){
        return true;
    }

    public int addTwoInt(int a, int b)
    {

        // Adding two integer value
        int sum = a + b;

        // Returning summation of two values
        return sum;
    }

     void checkAge(int age) {

        if (age < 18) {
            System.out.println("Access denied - You are not old enough!");

        } else {
            System.out.println("Access granted - You are old enough!");
        }
        for(int i=0;i<10;i++){
            System.out.println(i);
        }

        int forMyNum;

    }

}
