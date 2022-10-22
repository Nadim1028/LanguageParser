import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {
        String str = "";
        int counter = 0, curlyBraceCounter=0;
        ArrayList<String> methodLIst = new ArrayList<>();

        try {
            File myObj = new File("src/Math2.java");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if( data.contains("(") && data.contains(")") && !data.contains(";")&& checkReturnType(data)){
                    counter = 1;
                }
                if(counter >0){
                    if(data.contains("{"))
                        curlyBraceCounter++;
                    if( data.contains("}"))
                        curlyBraceCounter--;

                    str += data+"\n";
                }
                if(str.contains("}") && curlyBraceCounter==0){
                    counter = 0;
                    //System.out.println(str+"\n***************");
                    methodLIst.add(str);
                    //break;
                    str="";
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        System.out.println(methodLIst.size());

    }

    public static boolean checkReturnType(String data){
        return  (data.contains("int") || data.contains("void" ) || data.contains("float") ||
                data.contains("double") || data.contains("String") || data.contains("boolean"));
    }
}
