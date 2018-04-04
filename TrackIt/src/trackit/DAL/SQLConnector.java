package trackit.DAL;

import java.sql.*;

/**
 * DAL Layer: Handles the actual connection to the database.
 */
public class SQLConnector {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    private final String DRIVER = "com.mysql.jdbc.Driver";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private static SQLConnector singleton = null;
    private String databaseLocation = "localhost";
    private String port = "3306";
    private String databaseName = "TrackItDB";
    private String userName;
    private String password;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    private SQLConnector() {
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    private String getDatabaseURL()
            throws SQLException {
        return "";
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Static Methods">
    /**
     * Get the singleton instance.
     *
     * @return The singleton.
     */
    public static SQLConnector getInstance() {
        if (singleton == null) {
            singleton = new SQLConnector();
        }
        return singleton;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    public void setConnectionString(String userName) {
        this.userName = userName;
    }

    public void setConnectionString(String userName, String password) {
        this.setConnectionString(userName);
        this.password = password;
    }

    public void setConnectionString(
            String databaseLocation, String port, String databaseName) {
        this.databaseLocation = databaseLocation;
        this.port = port;
        this.databaseName = databaseName;
    }

    public void setConnectionString(String databaseLocation, String port,
            String databaseName, String userName) {
        this.setConnectionString(databaseLocation, port, databaseName);
        this.setConnectionString(userName);
    }

    public void setConnectionString(String databaseLocation, String port,
            String databaseName, String userName, String password) {
        this.setConnectionString(databaseLocation, port, databaseName);
        this.setConnectionString(userName, password);
    }

    public boolean isValidConnection() {
        boolean isValid = false;

        return isValid;
    }

    public Connection getConnection()
            throws SQLException {
        return DriverManager.getConnection(
                getDatabaseURL(), userName, password);
    }
    // </editor-fold>
}
