package trackit.DAL;

import java.sql.*;
import java.util.*;

/**
 * DAL Layer: Converts database values into object properties and vice versa.
 *
 * @param <T> The type of object that it is helping.
 */
public abstract class SQLHelper<T> {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    /**
     * This value can never be a primary key.
     */
    public final static Integer INVALID_PRIMARY_KEY = 0;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">

    private final SQLConnector sqlConn = SQLConnector.getInstance();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SQLHelper() {
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

    /**
     * Creates the SQL syntax necessary for executing a stored procedure.
     *
     * @param sprocName The name of the stored procedure to execute.
     * @param parameters A list of the parameters to pass into the stored
     * procedure. The key is the order the parameters should be used. The key's
     * value is the value to pass.
     * @return A valid SQL command for running the specified stored procedure.
     */
    private String buildSprocSyntax(String sprocName, HashMap<Integer, SprocParameter> parameters) {
        StringBuilder sb = new StringBuilder();
        //TODO:  code this
        return sb.toString();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Protected Methods">
    /**
     * Executes a stored procedure with the specified parameters.
     *
     * @param sprocName The name of the stored procedure to execute.
     * @param parameters A list of the parameters to pass into the stored
     * procedure. The key is the order the parameters should be used. The key's
     * value is the value to pass.
     * @return The ResultSet returned from the stored procedure.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    protected ResultSet execSproc(String sprocName, HashMap<Integer, SprocParameter> parameters)
            throws ClassNotFoundException, SQLException {
        ResultSet results;
        String sql = buildSprocSyntax(sprocName, parameters);

        try (Connection myConn = sqlConn.getConnection();
                PreparedStatement stmt = myConn.prepareCall(sql)) {
            results = stmt.executeQuery();
        }

        return results;
    }
// </editor-fold>
}
