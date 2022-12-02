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
//    public static void main(String[] args) throws IOException
//    {
//        ArrayList<String> methodList = new ArrayList<>();
//        ArrayList<String> methodSignatures = new ArrayList<>();
//        ArrayList<Integer> locOfMethods = new ArrayList<>();
//        ArrayList<Integer> argumentsPassedInMethods = new ArrayList<>();
//        ArrayList<Integer> functionCallsInMethods = new ArrayList<>();
//        ArrayList<Integer> returnStatements = new ArrayList<>();
//        ArrayList<Integer> numOfLocalVariables = new ArrayList<>();
//        ArrayList<Integer> numOfLoops = new ArrayList<>();
//        ArrayList<Integer> numOfConditions = new ArrayList<>();
//        RemoveComments removeComments = new RemoveComments();
//        removeComments.createUncommentedSourceCode("src/Math2.java");
//        File filePath = new File("src/clean_code.txt");
//        extractMethods(methodList,methodSignatures,filePath);
//        locOfMethods=getNumberOfLOC(methodList);
//        getArgumentsOfEachMethod(methodSignatures,argumentsPassedInMethods);
//        getNumberOfFunctionCalls(methodList,functionCallsInMethods);
//        getNumberOfReturnStatements(methodList,returnStatements);
//        getNumberOfLocalVariable(methodList,numOfLocalVariables);
//        getNumberOfConditionsAndLoops(methodList,numOfConditions,numOfLoops);
////        for (int num:numOfLoops){
////            System.out.println(num);
////        }
//
//
//        ArrayList<ArrayList<Integer>> metrics =new ArrayList<ArrayList<Integer>>();
//        metrics.add(locOfMethods);
//        metrics.add(argumentsPassedInMethods);
//        metrics.add(numOfLocalVariables);
//        metrics.add(functionCallsInMethods);
//        metrics.add(numOfConditions);
//        metrics.add(numOfLoops);
//        metrics.add(returnStatements);
//
//
//        System.out.println(locOfMethods);
//        System.out.println(argumentsPassedInMethods);
//        System.out.println(numOfLocalVariables);
//        System.out.println(functionCallsInMethods);
//        System.out.println(numOfConditions);
//        System.out.println(numOfLoops);
//        System.out.println(returnStatements);
//
//        int[] [] metricsValue = new int[methodList.size()][7];
//        for (int i=0;i<methodList.size();i++){
//            for(int j=0;j<7;j++){
//                metricsValue[i][j]= metrics.get(j).get(i);
//            }
//        }
//
//        for (int i = 0; i < metricsValue.length; i++) {
//            for (int j = 0; j < metricsValue[i].length; j++) {
//                System.out.print( metricsValue[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }

    public  int[][] geMetricsValueOfMethods(String filePath) throws IOException {
        ArrayList<String> methodList = new ArrayList<>();
        ArrayList<String> methodSignatures = new ArrayList<>();
        ArrayList<Integer> locOfMethods = new ArrayList<>();
        ArrayList<Integer> argumentsPassedInMethods = new ArrayList<>();
        ArrayList<Integer> functionCallsInMethods = new ArrayList<>();
        ArrayList<Integer> returnStatements = new ArrayList<>();
        ArrayList<Integer> numOfLocalVariables = new ArrayList<>();
        ArrayList<Integer> numOfLoops = new ArrayList<>();
        ArrayList<Integer> numOfConditions = new ArrayList<>();
        ArrayList<Integer> numOfAssignmentStatements = new ArrayList<>();
        ArrayList<Integer> numOfInputStatements = new ArrayList<>();
        ArrayList<Integer> numOfOutputStatements = new ArrayList<>();


        RemoveComments removeComments = new RemoveComments();
        removeComments.createUncommentedSourceCode(filePath);
        File file = new File("src/clean_code.txt");

        extractMethods(methodList,methodSignatures,file);
        locOfMethods=getNumberOfLOC(methodList);
        getArgumentsOfEachMethod(methodSignatures,argumentsPassedInMethods);
        getNumberOfFunctionCalls(methodList,functionCallsInMethods);
        getNumberOfReturnStatements(methodList,returnStatements);
        getNumberOfLocalVariable(methodList,numOfLocalVariables);
        getNumberOfConditionsAndLoops(methodList,numOfConditions,numOfLoops);
        getNumberOfAssignmentOperations(methodList,numOfAssignmentStatements,numOfLocalVariables,functionCallsInMethods);
        getNumberOfInputStatements(methodList,numOfInputStatements);

        ArrayList<ArrayList<Integer>> metrics =new ArrayList<ArrayList<Integer>>();
        metrics.add(locOfMethods);
        metrics.add(argumentsPassedInMethods);
        metrics.add(numOfLocalVariables);
        metrics.add(functionCallsInMethods);
        metrics.add(numOfConditions);
        metrics.add(numOfLoops);
        metrics.add(returnStatements);
        metrics.add(numOfAssignmentStatements);
        metrics.add(numOfInputStatements);
        //metrics.add(numOfOutputStatements);


        int[] [] metricsValue = new int[methodList.size()][metrics.size()];
        for (int i=0;i<methodList.size();i++){
            for(int j=0;j<metrics.size();j++){
                metricsValue[i][j]= metrics.get(j).get(i);
            }
        }

        return metricsValue;
    }

    public static void getNumberOfInputStatements(ArrayList<String> methodList, ArrayList<Integer> numOfInputStatements)
    {
        for(String method:methodList){
            Scanner scanner = new Scanner(method);
            int counter = 0;
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.contains("scanf") || line.contains("cin") || line.contains("nextInt()") || line.contains("nextLine()") || line.contains("nextDouble()")
                        || line.contains("nextFloat()") || line.contains("nextLong()") || line.contains("nextBoolean()"))
                {
                    if(line.contains("%d") || line.contains("%f") || line.contains("%lf") || line.contains("%d")
                            || line.contains("%s") || line.contains("%s"))
                    {
                        if(charCounter(line,',')>1)
                        {
                            for (int i = 0; i< charCounter(line,','); i++)
                            {
                                counter++;
                            }
                        }
                        else
                            counter++;
                    }

                    else if(line.contains(">>"))
                    {
                        if(charCounter(line,'>')>2)
                        {
                            for (int i = 0; i< charCounter(line,'>')/2; i++)
                            {
                                counter++;
                            }
                        }
                        else
                            counter++;
                    }
                    else
                        counter++;
                }
            }
            numOfInputStatements.add(counter);
        }
    }

    public static void getNumberOfAssignmentOperations(ArrayList<String> methodList, ArrayList<Integer> numOfAssignmentStatements, ArrayList<Integer> numOfLocalVariables, ArrayList<Integer> functionCallsInMethods)
    {
        for(int i=0; i< methodList.size();i++){
            Scanner scanner = new Scanner(methodList.get(i));
            int assignmentCounter = 0,localVariableInAssignment=0, functionCallInAssignment=0;
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.contains("=") && !(line.contains("=") && line.contains("for")) && !(line.contains("=")  && line.contains("if")) && line.contains(";") && !line.contains("print")
                ||  (charCounter(line,'=')>2 && line.contains("for")))  {
                    System.out.println(line);
                    String splitLine="";
                   if(!(charCounter(line,'=')>2 && line.contains("for")))
                        splitLine = line.split("=",2)[1];

                    if(splitLine.contains("(") && splitLine.contains(")") && splitLine.contains(";") && !splitLine.contains("=")
                            &&  !splitLine.contains("printf") && !splitLine.contains(" System.out.print")){
                        //System.out.println(splitLine);
                        functionCallInAssignment++;
                    }


                    if(charCounter(line,'=')>1 && !line.contains("for"))
                        assignmentCounter += charCounter(line,'=');
                    else
                        assignmentCounter++;

                    if(new LocalVariableCounter().hasContainsValidDataType(line) &&
                    !(line.contains(";")  && !line.contains("for") && !line.contains("print") &&
                            !line.contains("printf") && !line.contains(" System.out.print") && !( line.contains("=") &&
                            line.contains(")") && !(line.contains("nextInt()") || line.contains("nextLine()") ||
                            line.contains("nextDouble()") || line.contains("nextFloat()") ) ))){
                       // System.out.println("********** "+line);
                        localVariableInAssignment++;
                    }

                }

            }
            numOfAssignmentStatements.add(assignmentCounter);
            numOfLocalVariables.set(i,numOfLocalVariables.get(i)+localVariableInAssignment);
            functionCallsInMethods.set(i,functionCallsInMethods.get(i)+functionCallInAssignment);
        }
    }


    public static void getNumberOfConditionsAndLoops(ArrayList<String> methodList, ArrayList<Integer> numOfConditions,ArrayList<Integer> numOfLoops){
        for(String method:methodList){
            Scanner scanner = new Scanner(method);
            int conditionCounter = 0,loopCounter=0;
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.contains("if") || line.contains("else if") || line.contains("else")) {
                 //   System.out.println(line);
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
       // System.out.println("Size : "+methodList.size());
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
                   // System.out.println(line);
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
                        && !line.contains("scanf") &&  !line.contains("cout") &&  !line.contains("printf") && !line.contains(" System.out.print")){
                    //System.out.println(line);
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
