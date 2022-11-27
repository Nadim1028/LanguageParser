import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveComments {
    public void createUncommentedSourceCode(String sourceCodePath) throws IOException {
       // Scanner scanner = new Scanner(System.in);
        Path path = Path.of(sourceCodePath);//"src/code.txt");

        String content = Files.readString(path, StandardCharsets.US_ASCII);

        String str = content;//scanner.nextLine();
        String multiCommentRegex = "\\/\\*([\\S\\s]+?)\\*\\/", singleCommentRegex = "(/\\*((.|\n)*?)\\*/)|//.*";
        String outputFilePath = "src/clean_code"+".txt", uncommentedFilePath = "src/uncommented_file"+".txt"  ;
        String singleCommentRemoved = deleteComments(str,singleCommentRegex);
        String multiCommentRemoved = deleteComments(singleCommentRemoved,multiCommentRegex);
        generateFile(uncommentedFilePath,multiCommentRemoved);
        String newLineRemoved = removeNewLine(uncommentedFilePath);
        generateFile(outputFilePath,newLineRemoved);
    }

    public  String deleteComments(String myString,String regex)
    {
        String newString = "";
        Pattern commentaryPattern = Pattern.compile(regex);
        //Pattern commentaryPattern = Pattern.compile("(/\\*((.|\n)*?)\\*/)|//.*");
        //Pattern commentaryPattern = Pattern.compile("\\/\\*([\\S\\s]+?)\\*\\/");
        Matcher m = commentaryPattern.matcher(myString);
        newString += m.replaceAll("");

        return newString;
    }

    public  String removeNewLine(String filePath) throws FileNotFoundException
    {
        String fileString = "";
        FileInputStream fis=new FileInputStream(filePath);
        Scanner sc=new Scanner(fis);
        while(sc.hasNextLine())
        {
            String str = sc.nextLine();
            if(!str.isBlank()){
                fileString += str+"\n";
            }
        }
        sc.close();
        return fileString;
    }

    public  void generateFile(String filePath,String fileData){
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter( filePath));
            writer.write(fileData);

        }
        catch ( IOException e)
        {
        }
        finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
            }
        }
    }
}
