import java.util.ArrayList;
import java.util.Arrays;

public class NewTest {
    public static void main(String[] args) {
        ArrayList<String> metricsTypes = new ArrayList<String>(Arrays.asList("LOC","NumberOfParameters, NumberOfLocalVariable",
                "FunctionCall", "Conditions","Loops","ReturnStatements","AssignmentStatements","InputStatements"));

        System.out.println(metricsTypes.size());
        for (String str: metricsTypes
             ) {
            System.out.println(str);
        }
    }
}
