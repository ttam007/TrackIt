package trackittestdriver;

import java.sql.*;
//import java.util.Calendar;

import trackit.DAL.*;

/**
 *
 */
public class TrackItTestDriver {

    /**
     * Good for testing that the mysql-connector JAR files are loaded and one
     * can interact with the database.
     */
    private static void testMySQLJarConnection() {
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            Class.forName(myDriver);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/TrackItDB", "root", "password")) {
                /*
                Calendar calendar = Calendar.getInstance();
                java.sql.Date addDate = new java.sql.Date(calendar.getTime().getTime());
                 */
                String query = "insert into lookups (listName, listValue)" + "values (?, ?)";
                PreparedStatement prepStat = conn.prepareStatement(query);
                prepStat.setString(1, "toiletries");
                prepStat.setString(2, "soap");
                /*
                prepStat.setInt (2, 3);
                prepStat.setDate (3, addDate);
                 */
                prepStat.execute();
            }
        } catch (Exception e) {
            System.err.println("Exception detected!");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Test SQLConnector class.
     */
    private static void testSQLConnector() {
        //Change these values to do negative testing.
        String databaseLocation = "localhost";
        Integer port = 3306;
        String databaseName = "TrackItDB";
        String userName = "root";
        String password = ""; //Must set this value if using below.

        SQLConnector conn = SQLConnector.getInstance();
        try {
            //Only uncomment one of these to test the overloading.
            conn.setConnectionString(databaseLocation, port, databaseName, userName, password);
            //conn.setConnectionString(databaseLocation, port, databaseName, userName);
            //conn.setConnectionString(databaseLocation, port, databaseName);
            //conn.setConnectionString(userName, password);
            //conn.setConnectionString(userName);

            System.out.println("Username = " + conn.getUserName());
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //testMySQLJarConnection();
        testSQLConnector();
    }
}
