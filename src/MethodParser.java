import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodParser
{
    public static void main(String[] args) throws IOException
    {
        ArrayList<String> methodList = new ArrayList<>();
        ArrayList<String> methodSignatures = new ArrayList<>();
        ArrayList<Integer> locOfMethods = new ArrayList<>();
        ArrayList<Integer> argumentsPassedInMethods = new ArrayList<>();
        ArrayList<Integer> functionCallsInMethods = new ArrayList<>();
        ArrayList<Integer> returnStatements = new ArrayList<>();
        ArrayList<Integer> numOfLocalVariables = new ArrayList<>();
        ArrayList<Integer> numOfLoops = new ArrayList<>();
        ArrayList<Integer> numOfConditions = new ArrayList<>();
        RemoveComments removeComments = new RemoveComments();
        removeComments.createUncommentedSourceCode("src/Math1.java");
        File filePath = new File("src/clean_code.txt");
        extractMethods(methodList,methodSignatures,filePath);
        locOfMethods=getNumberOfLOC(methodList);
        getArgumentsOfEachMethod(methodSignatures,argumentsPassedInMethods);
        getNumberOfFunctionCalls(methodList,functionCallsInMethods);
        getNumberOfReturnStatements(methodList,returnStatements);
        getNumberOfLocalVariable(methodList,numOfLocalVariables);
        getNumberOfConditionsAndLoops(methodList,numOfConditions,numOfLoops);
//        for (int num:numOfLoops){
//            System.out.println(num);
//        }


        System.out.println(locOfMethods);
        System.out.println(argumentsPassedInMethods);
        System.out.println(numOfLocalVariables);
        System.out.println(functionCallsInMethods);
        System.out.println(numOfConditions);
        System.out.println(numOfLoops);
        System.out.println(returnStatements);
    }

    //blic static void

    public static void getNumberOfConditionsAndLoops(ArrayList<String> methodList, ArrayList<Integer> numOfConditions,ArrayList<Integer> numOfLoops){
        for(String method:methodList){
            Scanner scanner = new Scanner(method);
            int conditionCounter = 0,loopCounter=0;
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if((!line.contains(";") && (line.contains("if") || line.contains("else if"))) || line.contains("else")) {
                    conditionCounter += 1;
                }
                else if((!line.contains(";") && (line.contains("while") || line.contains("do"))) || (line.contains("for") && (line.contains("<") || line.contains("<")) && line.contains(";")) ) {
                    loopCounter += 1;
                }

            }
            numOfConditions.add(conditionCounter);
            numOfLoops.add(loopCounter);
        }
    }

    public static void getNumberOfLocalVariable(ArrayList<String> methodList, ArrayList<Integer> numOfLocalVariables){
        for(String method:methodList){
            Scanner scanner = new Scanner(method);
            int counter = 0;
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                counter += new LocalVariableCounter().getValue(line);

            }
            numOfLocalVariables.add(counter);
        }

    }

    public static void getNumberOfReturnStatements(ArrayList<String> methodList, ArrayList<Integer> returnStatements){
        for(int i=0;i<methodList.size();i++){
            Scanner scanner = new Scanner(methodList.get(i));
            int counter = 0;
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.contains("return")){
                    counter++;
                }
            }
            returnStatements.add(counter);
        }
    }

    public static void getNumberOfFunctionCalls(ArrayList<String> methodList, ArrayList<Integer> functionCallsInMethods){
        for(int i=0;i<methodList.size();i++){
            Scanner scanner = new Scanner(methodList.get(i));
            int counter = 0;
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.contains("(") && line.contains(")") && line.contains(";") && !line.contains("=")
                        &&  !line.contains("printf") && !line.contains(" System.out.print")){
                    counter++;
                }
            }
            functionCallsInMethods.add(counter);
        }
    }

    public static void extractMethods(ArrayList<String> methodLIst, ArrayList<String> methodSignatures,File filePath){
        int counter = 0, curlyBraceCounter=0;
        String str = "";

        try {
            //File myObj = new File("src/Math1.java");
            Scanner myReader = new Scanner(filePath);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if( data.contains("(")  && !data.contains(";")&& checkReturnType(data)){
                    methodSignatures.add(data);
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

    }

    public static void getArgumentsOfEachMethod(ArrayList<String> methodList, ArrayList<Integer> argumentsPassedInMethods){
        for(String method:methodList){
            argumentsPassedInMethods.add(identifyNumOfArguments(method));
        }
    }

    public static ArrayList<Integer> getNumberOfLOC(ArrayList<String> methods){
        ArrayList<Integer> locOfMethods = new ArrayList<>();
        for (String method: methods) {
            int loc=0;
           // System.out.println(method);
            Scanner sc=new Scanner(method);
            while(sc.hasNextLine())
            {
                String str = sc.nextLine();
                if(!str.isBlank() && str.trim().length()>1){
                    loc += 1;
                }
            }
            sc.close();
            //System.out.println(loc);
            locOfMethods.add(loc);
        }
        return locOfMethods;
    }



    public static boolean checkReturnType(String data){
        return  (data.contains("int") || data.contains("void" ) || data.contains("float") ||
                data.contains("double") || data.contains("String") || data.contains("bool") || data.contains("boolean") || data.contains("string"));
    }

    public static int identifyNumOfArguments(String str){
        String arguments = "";
        boolean checker=false;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='('){
               checker=true;
            }
            if(str.charAt(i)==')'){
                checker=false;
            }
            if(checker){
                //arguments.concat(String.valueOf(str.charAt(i)));
                if(str.charAt(i)!='(')
                    arguments += str.charAt(i);
            }
        }


        int numOfArguments = arguments.trim().isEmpty()  ? 0 :
                charCounter(arguments,',')>0 ? charCounter(arguments,',')+1  : 1;
        return numOfArguments;
    }

    public static int charCounter(String string, char myChar){
        int count =0;
        for(int i = 0; i < string.length(); i++) {
            if(string.charAt(i) == myChar)
                count++;
        }

        return count;
    }
}
