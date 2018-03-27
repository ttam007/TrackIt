package trackit;

import java.sql.*;

/**
 * Handles all aspects of interacting with the database.
 */
public class SQLConnector {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    private String DRIVER = "com.mysql.jdbc.Driver";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    /**
     * The singleton instance of this class.
     */
    private static SQLConnector singleton = null;
    private String databaseLocation;
    private String port;
    private String databaseName;
    private String userName;
    private String password;

    // </editor-fold>        
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * A private constructor to create a singleton.
     */
    private SQLConnector() {

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Builds the database URL from the connection properties.
     *
     * @return A valid database URL.
     * @throws SQLException if any required value is missing.
     */
    private String getDatabaseURL()
            throws SQLException {
        return "";
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Static Methods">
    /**
     * Gets the instance of the singleton object.
     *
     * @return The singleton.
     */
    public static SQLConnector getInstance() {
        if (SQLConnector.singleton == null) {
            SQLConnector.singleton = new SQLConnector();
        }
        return SQLConnector.singleton;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Instance Methods">
    /**
     * Sets all the values needed for a valid connection string. Based off of
     * https://www.javatpoint.com/example-to-connect-to-the-mysql-database
     *
     * @param databaseLocation Can be either the computer name, if named pipes
     * is turned on, or the IP address of the computer. If the database is on
     * the same machine as this program, then "localhost" can be used, which is
     * the default.
     * @param port The port number needed to connect to MySQL database.
     * @param databaseName The name of the database that will store this
     * program's data.
     * @param userName The database user name with enough permissions to do
     * basic CRUD operations.
     * @param password The password for <userName>.
     */
    public void setConnectionString(String databaseLocation, String port,
            String databaseName,
            String userName, String password) {
        this.databaseLocation = databaseLocation;
        this.port = port;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Validates the connection string data against the database.
     *
     * @return True = the connection string is correct; False = the connection
     * string is incorrect.
     */
    public boolean validateConnection() {
        boolean isValid = false;

        return isValid;
    }

    public Connection getConnection()
            throws SQLException {
        return DriverManager.getConnection(
                getDatabaseURL(), this.userName, this.password);
    }
    // </editor-fold>
}
