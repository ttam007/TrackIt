package trackit.DAL;

import java.sql.*;
import java.util.*;

/**
 * Handles all the CRUD operations.
 *
 * @param <T> The object type in the database that this class will work with.
 */
public abstract class SQLHelper<T> {

    private final SQLConnector sqlConn = SQLConnector.getInstance();

    /**
     * Builds the SQL for running a stored procedure.
     *
     * @param sprocName The name of the stored procedure to be run.
     * @param parameters The parameters that will be passed into the stored
     * procedure. The key <Integer> is the position of the parameter. The value
     * <String> is the value.
     * @return A SQL statement correctly formated for running the specified
     * stored procedure.
     */
    private String buildSprocSyntax(String sprocName, HashMap<Integer, String> parameters) {
        return "";
    }

    protected ResultSet execSproc(String sprocName, HashMap<Integer, String> parameters)
            throws SQLException {
        ResultSet results = null;
        try (Connection myConn = sqlConn.getConnection()) {
            String sql = buildSprocSyntax(sprocName, parameters);
            PreparedStatement stmt = myConn.prepareCall(sql);
            results = stmt.executeQuery();
        } catch (Exception ex) {
        }
        return results;
    }
}
