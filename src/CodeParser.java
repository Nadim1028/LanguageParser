import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CodeParser
{
    public static void main(String[] args) throws FileNotFoundException
    {

        ArrayList<String> variableList = new ArrayList<>();
        ArrayList<String> variableConversion = new ArrayList<>();
        ArrayList<String> userInputConversion = new ArrayList<>();
        ArrayList<String> conditionConversion = new ArrayList<>();
        ArrayList<String> loopConversion = new ArrayList<>();

        String str = null;
        String[] variables=null;
        int variableCounter=0;
        int numOfLOC, numOfArgumentsPassed,numOfLocalVariable,numOfGlobalVariable,
                numOfFunctionCalls, numOfConditions, numOfLoops, numOfReturnStatements;

        //File myObj = new File("src/Math1.java");
            File myObj = new File("src/Java.txt");

            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
        String line = myReader.nextLine();

        //variable conversion
        if(hasContainsValidDataType(line) && line.contains(";")  && !line.contains("for") && !line.contains("print") &&
                !line.contains("printf") && !line.contains(" System.out.print") && !( line.contains("=") &&
                line.contains(")") && !(line.contains("nextInt()") || line.contains("nextLine()") || line.contains("nextDouble()")
                || line.contains("nextFloat()") ) ) )
        {
            System.out.println(line);

            if(line.contains(",")){
                //System.out.println("A"+variableCounter);
                variableCounter += charCounter(line,',')+1;

                for(int i = 0; i<= charCounter(line,','); i++){
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
        if(line.contains("scanf") || line.contains("cin") || line.contains("nextInt()") || line.contains("nextLine()") || line.contains("nextDouble()")
                || line.contains("nextFloat()") || line.contains("nextLong()") || line.contains("nextBoolean()"))
        {
            if(line.contains("%d") || line.contains("%f") || line.contains("%lf") || line.contains("%d") || line.contains("%s") || line.contains("%s")){
                if(charCounter(line,',')>1){
                    for (int i = 0; i< charCounter(line,','); i++){
                        userInputConversion.add("INPUT");
                    }
                }

                else
                    userInputConversion.add("INPUT");
            }

            else if(line.contains(">>")){
                if(charCounter(line,'>')>2){
                    for (int i = 0; i< charCounter(line,'>')/2; i++){
                        userInputConversion.add("INPUT");
                    }
                }

                else
                    userInputConversion.add("INPUT");
            }

            else
                userInputConversion.add("INPUT");
        }


        //condition conversion
        if((!line.contains(";") && (line.contains("if") || line.contains("else if"))) || line.contains("else")){
            conditionConversion.add("CONDITION");
        }

        //loop conversion
        if((!line.contains(";") && (line.contains("while") || line.contains("do"))) || line.contains("for")){
            //conditionConversion.add("LOOP");
            loopConversion.add("LOOP");
        }
    }
            System.out.println(variableConversion);
            System.out.println(userInputConversion);
            System.out.println(conditionConversion);
    }

    public static boolean hasContainsValidDataType(String str){
        return  (str.contains("char") || str.contains("int") || str.contains("void" ) || str.contains("float") ||
                str.contains("double") || str.contains("String") || str.contains("bool") || str.contains("boolean") || str.contains("string"));
    }

    public static int charCounter(String string, char mychar){
        int count =0;
        for(int i = 0; i < string.length(); i++) {
            if(string.charAt(i) == mychar)
                count++;
        }

        return count;
    }

    public static void addTemplateConversion(String line, ArrayList<String> variableConversion){
        if(line.contains("int") || line.contains("float") ||
                line.contains("double") ){

            variableConversion.add("DataType1 X;");
        }

        if(line.contains("String") || line.contains("string") || line.contains("char")){
            variableConversion.add("DataType2 X;");
        }
    }
}

