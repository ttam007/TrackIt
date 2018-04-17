package trackittestdriver;

/**
 * Starts all the unit tests
 */
public class TrackItTestDriver {

    /**
     * Uncomment as necessary to test different classes and functions.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Must set this parameter to get a valid connection.
        DALTestDriver dal = new DALTestDriver("Password");

        //dal.testMySQLJarConnection();
        //dal.testSQLConnector();
        //dal.testSQLHelperSupplier();
        //dal.testSQLHelperOrder();
        //dal.testSQLHelperInventoryItem();
        //dal.testSQLHelperOrderItem();
    }
}
