package trackit.DAL;

import java.sql.*;
import trackit.*;

/**
 * DAL Layer: Handles the actual connection to the database.
 *
 * @author Bond
 */
public class SQLConnector {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private volatile static SQLConnector singleton = null;
    private String databaseLocation = "localhost";
    private Integer port = 3306;
    private String databaseName = "TrackItDB";
    private String userName;
    private String password;
    private String errorMessage = "";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Can not create an instance of this class except via the getInstance()
     * method.
     */
    private SQLConnector() {
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Gets the database URL.
     *
     * @return A database URL of the form jdbc:subprotocol:subname
     * @throws SQLException
     */
    private String getDatabaseURL()
            throws SQLException {
        return String.format("jdbc:mysql://%s:%d/%s?autoReconnect=true&useSSL=false",
                this.databaseLocation, this.port, this.databaseName);
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

    /**
     *
     * @param userName
     */
    public void setConnectionString(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @param userName
     * @param password
     */
    public void setConnectionString(String userName, String password) {
        this.setConnectionString(userName);
        this.password = password;
    }

    /**
     *
     * @param databaseLocation
     * @param port
     * @param databaseName
     */
    public void setConnectionString(
            String databaseLocation, Integer port, String databaseName) {
        this.databaseLocation = databaseLocation;
        this.port = port;
        this.databaseName = databaseName;
    }

    /**
     *
     * @param databaseLocation
     * @param port
     * @param databaseName
     * @param userName
     */
    public void setConnectionString(String databaseLocation, Integer port,
            String databaseName, String userName) {
        this.setConnectionString(databaseLocation, port, databaseName);
        this.setConnectionString(userName);
    }

    /**
     *
     * @param databaseLocation
     * @param port
     * @param databaseName
     * @param userName
     * @param password
     */
    public void setConnectionString(String databaseLocation, Integer port,
            String databaseName, String userName, String password) {
        this.setConnectionString(databaseLocation, port, databaseName);
        this.setConnectionString(userName, password);
    }

    /**
     *
     * @return
     */
    public boolean isValidConnection() {
        boolean isValid = false;

        try (Connection conn = this.getConnection()) {
            isValid = true;
        } catch (SQLException exSQL) {
            this.errorMessage = Utilities.buildErrorMessage(exSQL);
        } catch (Exception ex) {
            this.errorMessage = ex.getLocalizedMessage();
        }

        return isValid;
    }

    /**
     * Gets the connection that will be used for all SQL CRUD operations.
     *
     * @return A database connection.
     * @throws ClassNotFoundException If MySQL driver isn't installed.
     * @throws SQLException If the connection to the database is invalid.
     */
    public Connection getConnection()
            throws SQLException, Exception {
        Class.forName(DRIVER).newInstance();
        return DriverManager.getConnection(
                getDatabaseURL(), this.userName, this.password);
    }

    /**
     * Returns the last error generated by this class.
     *
     * @return The error message.
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Gets the User Name that was previously set.
     *
     * @return The User Name.
     */
    public String getUserName() {
        return this.userName;
    }
    // </editor-fold>
}
