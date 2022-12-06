import java.io.IOException;
import java.util.ArrayList;

public class TestMain
{
    public static void main(String[] args) throws IOException
    {
        MethodParser methodParser = new MethodParser("src/data.txt");
        int[][] metricsValues1 = methodParser.geMetricsValueOfMethods();
        ArrayList<String> methodList1;
        methodList1   = methodParser.getMethods();
        System.out.println(methodList1);
        System.out.println("*************");
        MethodParser methodParser2 = new MethodParser("src/data2.txt");
        int[][] metricsValues2 = methodParser2.geMetricsValueOfMethods();
        ArrayList<String> methodList2;
        methodList2 = methodParser2.getMethods();
        System.out.println(methodList2);

        ArrayList<int[]> rows = new ArrayList<>();
        ArrayList<int[]> rows2 = new ArrayList<>();

        for (int i = 0; i < metricsValues1.length; i++)
        {
            int[] eachRow = new int[metricsValues1[i].length];
            for (int j = 0; j < metricsValues1[i].length; j++)
            {
                eachRow[j] = metricsValues1[i][j];
                System.out.print( metricsValues1[i][j] + " ");
            }
            rows.add(eachRow);
            System.out.println();
        }
        System.out.println("****************");

        for (int i = 0; i < metricsValues2.length; i++) {
            int[] eachRow = new int[metricsValues2[i].length];
            for (int j = 0; j < metricsValues2[i].length; j++) {
                eachRow[j] = metricsValues2[i][j];
                System.out.print( metricsValues2[i][j] + " ");
            }
            rows2.add(eachRow);
           System.out.println();
        }
        compareMethods(rows,rows2,methodList1,methodList2);
    }

    public static void compareMethods(ArrayList<int[]> rows, ArrayList<int[]> rows2, ArrayList<String> methodList1, ArrayList<String> methodList2)
    {
       //System.out.println(methodList1);

        CosineAngleCalculator calculator = new CosineAngleCalculator();

        ArrayList<double[]> plagiarizedMethodIndex = new ArrayList<>();

        for(int i=0;i<rows.size();i++){
            double max = -1;
            int index = 0;
            for(int j=0;j<rows2.size();j++){
                double value = calculator.getCosineSimilarity(rows.get(i),rows2.get(j));
               System.out.println("Value("+i+","+j+") = "+value);
               if(value>0.99){
                   System.out.println("Plagiarized methods("+i+","+j+") = "+value);
                   plagiarizedMethodIndex.add(new double[]{i,j,value});
               }
                if(value>max){
                    max =  value;
                    index = j;
                }
            }

           /* //System.out.println("max = "+max+" , index = "+index);
            System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            System.out.println("File 1 -> method = "+i);
            String str = new CodeParser().getTemplateConversion(methodList1.get(i));
            //System.out.println(str);
            System.out.println("File 2 -> method = "+index);
            System.out.println(new CodeParser().getTemplateConversion(methodList2.get(index)));
            //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");*/

        }

        for (double[] arr:plagiarizedMethodIndex
        ) {
            System.out.println(arr[0]+" , "+arr[1]+" = "+arr[2]);
        }
    }
}
