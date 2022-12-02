import java.io.IOException;
import java.util.ArrayList;

public class TestMain
{
    public static void main(String[] args) throws IOException
    {
        MethodParser methodParser = new MethodParser();
        int[][] metricsValues1 = methodParser.geMetricsValueOfMethods("src/CPP.txt");
        System.out.println("*************");
        int[][] metricsValues2 = methodParser.geMetricsValueOfMethods("src/CPP2.txt");
        ArrayList<int[]> rows = new ArrayList<>();
        ArrayList<int[]> rows2 = new ArrayList<>();

        for (int i = 0; i < metricsValues1.length; i++) {
            int[] eachRow = new int[metricsValues1[i].length];
            for (int j = 0; j < metricsValues1[i].length; j++) {
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

        compareMethods(rows,rows2);
    }

    public static void compareMethods(ArrayList<int[]> rows,ArrayList<int[]> rows2){
        CosineAngleCalculator calculator = new CosineAngleCalculator();
        for(int i=0;i<rows.size();i++){
            for(int j=0;j<rows2.size();j++){
                double value = calculator.getCosineSimilarity(rows.get(i),rows2.get(j));
                System.out.println("Value("+i+","+j+") = "+value);

            }
        }
    }
}
