package trackit.DAL;

import java.sql.*;
import java.util.*;

/**
 * DAL Layer: Converts database values into object properties and vice versa.
 *
 * @param <T> The type of object that it is helping.
 * @author Bond
 */
public abstract class SQLHelper<T>
        implements ISQLHelper<T> {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    /**
     * This value can never be a primary key.
     */
    public final static Integer INVALID_PRIMARY_KEY = 0;
    /**
     * Should be final, but java doesn't allow this with the inheritance that we
     * are using. Thus, it is only set in child constructors.
     */
    public static String COLUMN_PK;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Protected Fields">

    /**
     *
     */
    protected final SQLConnector sqlConn = SQLConnector.getInstance();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default Constructor.
     */
    protected SQLHelper() {
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Sets the parameter value in the stored procedure. The assumption is that
     * the calling code will control the order the parameter is set in the
     * CallableStatment.
     *
     * @param stmt The Callable Statement (sproc) that that needs the value set.
     * @param aParam The parameter object that has the value to be used.
     * @returns True = registered as an OUT parameter; False = IN parameter
     * only.
     * @throws SQLException
     */
    private boolean setStatement(CallableStatement stmt, SprocParameter aParam)
            throws SQLException {
        boolean isOUTParam = false;
        if (aParam.getDirection() == ParameterDirection.OUT
                || aParam.getDirection() == ParameterDirection.INOUT) {
            isOUTParam = true;
            stmt.registerOutParameter(aParam.getName(), aParam.getValueType());
        }

        if (aParam.getDirection() == ParameterDirection.IN
                || aParam.getDirection() == ParameterDirection.INOUT) {

            if (aParam.isNull()) {
                stmt.setNull(aParam.getName(), aParam.getValueType());
            } else {
                switch (aParam.getValueType()) {
                    case Types.DATE:
                        stmt.setDate(aParam.getName(), java.sql.Date.valueOf(aParam.getValue()));
                        break;
                    case Types.DOUBLE:
                        stmt.setDouble(aParam.getName(), Double.parseDouble(aParam.getValue()));
                        break;
                    case Types.INTEGER:
                        stmt.setInt(aParam.getName(), Integer.parseInt(aParam.getValue()));
                        break;
                    case Types.VARCHAR:
                        stmt.setString(aParam.getName(), aParam.getValue());
                        break;
                    default:
                        break;
                }
            }
        }

        return isOUTParam;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Protected Methods">
    /**
     * Creates the SQL syntax necessary for executing a stored procedure.
     *
     * @param sprocName The name of the stored procedure to execute.
     * @param parameterCount A number of parameters in the sproc.
     * @return A valid SQL command for running the specified stored procedure.
     */
    protected String buildSprocSyntax(String sprocName, int parameterCount) {
        StringBuilder sb = new StringBuilder();

        sb.append("{ CALL ");
        sb.append(sprocName);
        if (parameterCount > 0) {
            sb.append("(");
        }

        //Process parameters
        boolean isFirstParameter = true;
        for (int i = 0; i < parameterCount; i++) {
            //All parameters after the first one are prepended with a comma.
            if (!isFirstParameter) {
                sb.append(",");
            }
            sb.append("?");

            //After processing first parameter, change isFirstParameter boolean.
            if (isFirstParameter) {
                isFirstParameter = false;
            }
        }

        if (parameterCount > 0) {
            sb.append(")");
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Sets all parameters in the calling statement.
     *
     * @param stmt The CallableStatment that needs parameters set.
     * @param parameters The parameters to be set for the specified statement.
     * @return A list of the indices of the OUT parameters.
     * @throws SQLException
     * @throws Exception
     */
    protected ArrayList<Integer> setParameters(CallableStatement stmt,
            HashMap<Integer, SprocParameter> parameters)
            throws SQLException, Exception {
        final ArrayList<Integer> outParams = new ArrayList<>(); //index of OUT parameters

        for (int i = 0; i < parameters.size(); i++) {
            SprocParameter aParam = parameters.get(i);
            if (setStatement(stmt, aParam)) {
                outParams.add(i);
            }
        }

        return outParams;
    }

    /**
     * Gets the value of the OUT parameter after stored procedure execution.
     *
     * @param stmt The CallableStatement that called the stored procedure.
     * @param aParam The SprocParameter that we need the value for.
     * @return The value of the OUT parameter as a String.
     * @throws SQLException
     * @throws Exception
     */
    protected String getOutParameterValue(CallableStatement stmt, SprocParameter aParam)
            throws SQLException, Exception {
        String outValue = null;
        switch (aParam.getValueType()) {
            case Types.DATE:
                java.sql.Date aDate = stmt.getDate(aParam.getName());
                outValue = aDate.toString();
                break;
            case Types.DOUBLE:
                Double aDouble = stmt.getDouble(aParam.getName());
                outValue = aDouble.toString();
                break;
            case Types.INTEGER:
                Integer anInteger = stmt.getInt(aParam.getName());
                outValue = anInteger.toString();
                break;
            case Types.VARCHAR:
                outValue = stmt.getString(aParam.getName());
                break;
            default:
                break;
        }
        return outValue;
    }

    /**
     * Converts a ResultSet to T. The current row of the ResultSet will be used
     * to populate T.
     *
     * @param rs The ResultSet generated by the stored procedure.
     * @return T, with all values from the current row of the ResultSet.
     * @throws SQLException
     */
    protected abstract T convertResultSetToObject(ResultSet rs)
            throws SQLException;

    /**
     * Executes a stored procedure with the specified parameters.
     *
     * @param sprocName The name of the stored procedure to execute.
     * @param parameters A zero-based indexed list of the parameters to pass
     * into the stored procedure. The key is the order the parameters should be
     * used. The key's value is the parameter data.
     * @return A list of all T's returned from the stored procedure. This will
     * be empty if there are no result sets.
     * @throws SQLException
     * @throws Exception
     */
    protected abstract ArrayList<T> execSproc(String sprocName, HashMap<Integer, SprocParameter> parameters)
            throws SQLException, Exception;/* {
        ArrayList<T> results = new ArrayList<>();

        String sql = buildSprocSyntax(sprocName, parameters.size());
        System.out.println("execSproc's sql = " + sql);
        try (Connection myConn = sqlConn.getConnection();
                CallableStatement stmt = myConn.prepareCall(sql)) {

            final ArrayList<Integer> outParams = setParameters(stmt, parameters);

            //Get the result set, if any.
            if (stmt.execute()) {
                ResultSet rs = stmt.getResultSet();
                while (rs.next()) {
                    T aBiler = new T();

                    results.add(aBiler);
                }
            }

            //Retrieve OUT parameters.
            for (int aParamIndex : outParams) {
                SprocParameter aParam = parameters.get(aParamIndex);
                aParam.setValue(String.valueOf(stmt.getInt(aParamIndex)));
            }
        }

        return results;
    }*/

    @Override
    public ArrayList<Integer> insertAll(List<T> aList)
            throws SQLException, Exception {
        ArrayList<Integer> primaryKeys = new ArrayList<>();
        for (T anObj : aList) {
            primaryKeys.add(insert(anObj));
        }
        return primaryKeys;
    }

    @Override
    public abstract Integer insert(T anObject)
            throws SQLException, Exception;

    @Override
    public void updateAll(List<T> aList)
            throws SQLException, Exception {
        for (T anObj : aList) {
            update(anObj);
        }
    }

    @Override
    public abstract void update(T anObject)
            throws SQLException, Exception;

    @Override
    public void deleteAll(List<Integer> primaryKeys)
            throws SQLException, Exception {
        for (Integer aPK : primaryKeys) {
            delete(aPK);
        }
    }

    @Override
    public abstract void delete(Integer primaryKey)
            throws SQLException, Exception;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Checks to see if any column that is not nullable has a null value.
     *
     * @param columnName The column to check.
     * @param aValue The value to check.
     * @return True = either column allows nulls or value is not null; False =
     * column doesn't allow nulls and value is null.
     */
    public boolean tryNullCheck(String columnName, java.sql.Date aValue) {
        try {
            doNullCheck(columnName, aValue);
            return true;
        } catch (SQLException exSQL) {
            return false;
        }
    }

    /**
     * Checks to see if any column that is not nullable has a null value.
     *
     * @param columnName The column to check.
     * @param aValue The value to check.
     * @return Either the parameter aValue or throws NonNullableValueException.
     * @throws SQLException When aValue is NULL and NULL is not allowed.
     */
    public abstract java.sql.Date doNullCheck(String columnName, java.sql.Date aValue)
            throws SQLException;

    /**
     * Checks to see if any column that is not nullable has a null value.
     *
     * @param columnName The column to check.
     * @param aValue The value to check.
     * @return True = either column allows nulls or value is not null; False =
     * column doesn't allow nulls and value is null.
     */
    public boolean tryNullCheck(String columnName, Double aValue) {
        try {
            doNullCheck(columnName, aValue);
            return true;
        } catch (SQLException exSQL) {
            return false;
        }
    }

    /**
     * Checks to see if any column that is not nullable has a null value.
     *
     * @param columnName The column to check.
     * @param aValue The value to check.
     * @return Either the parameter aValue or throws NonNullableValueException.
     * @throws SQLException When aValue is NULL and NULL is not allowed.
     */
    public abstract Double doNullCheck(String columnName, Double aValue)
            throws SQLException;

    /**
     * Checks to see if any column that is not nullable has a null value.
     *
     * @param columnName The column to check.
     * @param aValue The value to check.
     * @return True = either column allows nulls or value is not null; False =
     * column doesn't allow nulls and value is null.
     */
    public boolean tryNullCheck(String columnName, Integer aValue) {
        try {
            doNullCheck(columnName, aValue);
            return true;
        } catch (SQLException exSQL) {
            return false;
        }
    }

    /**
     * Checks to see if any column that is not nullable has a null value.
     *
     * @param columnName The column to check.
     * @param aValue The value to check.
     * @return Either the parameter aValue or throws NonNullableValueException.
     * @throws SQLException When aValue is NULL and NULL is not allowed.
     */
    public abstract Integer doNullCheck(String columnName, Integer aValue)
            throws SQLException;

    /**
     * Checks to see if any column that is not nullable has a null value.
     *
     * @param columnName The column to check.
     * @param aValue The value to check.
     * @return True = either column allows nulls or value is not null; False =
     * column doesn't allow nulls and value is null.
     */
    public boolean tryNullCheck(String columnName, String aValue) {
        try {
            doNullCheck(columnName, aValue);
            return true;
        } catch (SQLException exSQL) {
            return false;
        }
    }

    /**
     * Checks to see if any column that is not nullable has a null value.
     *
     * @param columnName The column to check.
     * @param aValue The value to check.
     * @return Either the parameter aValue or throws NonNullableValueException.
     * @throws SQLException When aValue is NULL and NULL is not allowed.
     */
    public abstract String doNullCheck(String columnName, String aValue)
            throws SQLException;
    // </editor-fold>
}
