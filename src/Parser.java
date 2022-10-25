import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser
{
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<String> variableList = new ArrayList<>();
        String str = null;
        String[] variables=null;
        int variableCounter=0;

        //File myObj = new File("src/Math1.java");
            File myObj = new File("src/data.txt");

            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();

                if(hasContainsValidDataType(line) && line.contains(";")  && !line.contains("for") && !line.contains("printf") && !line.contains("println")){
                    if(line.contains(",")){
                        //System.out.println("CC : "+commaCounter(line));
                        System.out.println("A"+variableCounter);
                        variableCounter += commaCounter(line)+1;
                    }
                    else if(!line.contains(",")){
                        System.out.println("A"+variableCounter);
                        variableCounter += 1;
                    }

                }
                //System.out.println(str);
            }


            System.out.println(variableCounter);
    }

    public static boolean hasContainsValidDataType(String str){
        return  (str.contains("int") || str.contains("void" ) || str.contains("float") ||
                str.contains("double") || str.contains("String") || str.contains("bool") || str.contains("boolean") || str.contains("string"));
    }

    public static int commaCounter(String string){
        int count =0;
        for(int i = 0; i < string.length(); i++) {
            if(string.charAt(i) == ',')
                count++;
        }

        return count;
    }

}

