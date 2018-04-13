package trackittestdriver;

import trackit.DAL.ASupplier;
import java.sql.*;
import java.util.*;
import trackit.*;
import trackit.DAL.*;

/**
 * Tests the DAL Layer.
 *
 * @author Bond
 */
public class DALTestDriver {

    private final String password;

    public DALTestDriver(String password) {
        this.password = password;
        setDefaultConnection();
    }

    private void setDefaultConnection() {
        String databaseLocation = "localhost";
        Integer port = 3306;
        String databaseName = "TrackItDB";
        String userName = "root";

        SQLConnector conn = SQLConnector.getInstance();
        try {
            conn.setConnectionString(databaseLocation, port, databaseName, userName, password);
            if (conn.isValidConnection()) {
                System.out.println("Valid Connection");
            } else {
                System.out.println("Invalid Connection");
            }
        } catch (Exception ex) {
            System.out.println("Error = " + ex.getLocalizedMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="SQL Connections">
    /**
     * Good for testing that the mysql-connector JAR files are loaded and one
     * can interact with the database.
     */
    public void testMySQLJarConnection() {
        System.out.println("\ntestMySQLJarConnection");

        try {
            String myDriver = "com.mysql.jdbc.Driver";
            Class.forName(myDriver);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/TrackItDB", "root", password)) {
                String query = "insert into lookups (listName, listValue) values (?, ?)";
                PreparedStatement prepStat = conn.prepareStatement(query);
                prepStat.setString(1, "toiletries");
                prepStat.setString(2, "soap");
                prepStat.execute();
            }
        } catch (ClassNotFoundException exCNF) {
            System.err.println("Class Not Found Exception detected!");
            System.err.println(exCNF.getMessage());
        } catch (SQLException exSQL) {
            System.err.println("SQL Exception detected!");
            System.err.println(exSQL.getMessage());
        }
    }

    /**
     * Test SQLConnector class.
     */
    public void testSQLConnector() {
        System.out.println("\ntestSQLConnector");

        //Change these values to do negative testing.
        String databaseLocation = "localhost";
        Integer port = 3306;
        String databaseName = "TrackItDB";
        String userName = "root";

        SQLConnector conn = SQLConnector.getInstance();
        try {
            //Only uncomment one of these to test the overloading.
            conn.setConnectionString(databaseLocation, port, databaseName, userName, password);
            //conn.setConnectionString(databaseLocation, port, databaseName, userName);
            //conn.setConnectionString(databaseLocation, port, databaseName);
            //conn.setConnectionString(userName, password);
            //conn.setConnectionString(userName);

            System.out.println("Username = " + conn.getUserName());

            /*Depending on valid in local variables and the overloaded method 
            used, one might or might not get a valid connection.  Must adjust 
            accordingly for the test being run.
             */
            if (conn.isValidConnection()) {
                System.out.println("Valid Connection");
                System.out.println("Connection = " + conn.getConnection());
            } else {
                System.out.println("Invalid Connection");
                System.out.println("SQLConnector's errorMessage = " + conn.getErrorMessage());
            }

        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
            System.out.println("SQLConnector's errorMessage = " + conn.getErrorMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SQLHelperSupplier Class">
    private void printSupplier(ASupplier aSupplier) {
        if (aSupplier == null) {
            System.out.println("Supplier is null.");
        } else {
            System.out.println(String.format("Supplier:  PK = %d; nickname = %s; URL = %s",
                    aSupplier.getPrimaryKey(), aSupplier.getNickname(), aSupplier.getUrl()));
        }
    }

    /**
     * Tests all CRUD operations for the ASupplier's sprocs.
     */
    public void testSQLHelperSupplier() {
        System.out.println("\n\ntestSQLHelperSupplier");

        SQLHelperSupplier helper = new SQLHelperSupplier();
        ASupplier aSupplier;
        Integer pk;
        try {
            System.out.println("\nSelectAll");
            ArrayList<ASupplier> suppliers = helper.selectAll();
            for (ASupplier anItem : suppliers) {
                printSupplier(anItem);
            }

            System.out.println("\nSelectOne");
            aSupplier = helper.selectOne(2);
            printSupplier(aSupplier);

            System.out.println("\nInsert");
            //Must delete "Etsy" from the database before testing this.
            ASupplier aSupplierInsert = new ASupplier("Etsy", "https://www.etsy.com");
            pk = helper.insert(aSupplierInsert);
            aSupplier = helper.selectOne(pk);
            printSupplier(aSupplier);

            System.out.println("\nUpdate");
            ASupplier aSupplierUpdate = new ASupplier(pk, "Etsy", "https://www.etsy-orders.com");
            helper.update(aSupplierUpdate);
            aSupplier = helper.selectOne(aSupplierUpdate.getPrimaryKey());
            printSupplier(aSupplier);

            System.out.println("\nDelete");
            pk = 2;
            helper.delete(pk);
            aSupplier = helper.selectOne(pk);
            printSupplier(aSupplier);
        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }
    }
}
