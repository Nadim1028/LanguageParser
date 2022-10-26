import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser
{
    public static void main(String[] args) throws FileNotFoundException
    {

        ArrayList<String> variableList = new ArrayList<>();
        ArrayList<String> variableConversion = new ArrayList<>();
        ArrayList<String> userInputConversion = new ArrayList<>();

        String str = null;
        String[] variables=null;
        int variableCounter=0;

        //File myObj = new File("src/Math1.java");
            File myObj = new File("src/data2.java");

            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();

                //variable conversion
                if(hasContainsValidDataType(line) && line.contains(";")  && !line.contains("for") &&
                        !line.contains("printf") && !line.contains("println") && !( line.contains("=") &&
                        line.contains(")") && !(line.contains("nextInt()") || line.contains("nextLine()") || line.contains("nextDouble()")
                        || line.contains("nextFloat()") ) ) )

                {
                    System.out.println(line);

                    if(line.contains(",")){
                        //System.out.println("A"+variableCounter);
                        variableCounter += commaCounter(line)+1;

                        for(int i=0;i<= commaCounter(line);i++){
                            addTemplateConversion(line,variableConversion);
                        }

                    }
                    else if(!line.contains(",")){
                        //System.out.println("A"+variableCounter);
                        variableCounter += 1;
                        addTemplateConversion(line,variableConversion);
                    }

                }

                //user input conversion
                if(line.contains("nextInt()") || line.contains("nextLine()") || line.contains("nextDouble()")
                        || line.contains("nextFloat()") || line.contains("nextLong()") || line.contains("nextBoolean()"))
                {
                    userInputConversion.add("INPUT");
                }
            }
            System.out.println(variableConversion);
            System.out.println(userInputConversion);
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

    public static void addTemplateConversion(String line, ArrayList<String> variableConversion){
        if(line.contains("int") || line.contains("float") ||
                line.contains("double") ){

            variableConversion.add("DataType1 X;");
        }

        if(line.contains("String") || line.contains("string")){
            variableConversion.add("DataType2 X;");
        }
    }
}

