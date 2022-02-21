import SQL.DBUtils;
import Testers.Test;

public class Program {
    public static void main(String[] args) {
        // read the README.txt file before running
        Test.dBDrop();
        Test.dBStart();
//      Test.createEntriesToDB();
        Test.testAll();

    }
}
