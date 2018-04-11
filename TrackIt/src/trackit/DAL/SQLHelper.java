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

    public String COLUMN_PK;
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
     * value is the parameter data.
     * @param returnsResultSet True = a result set is expected from the sproc;
     * False = no result set will be returned.
     * @return A valid SQL command for running the specified stored procedure.
     */
    private String buildSprocSyntax(String sprocName, int parameterCount,
            boolean returnsResultSet) {
        StringBuilder sb = new StringBuilder();

        if (returnsResultSet) {
            sb.append("{? = ");
        }
        sb.append("CALL ");
        sb.append(sprocName);
        sb.append("(");

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

        sb.append(")}");
        return sb.toString();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Protected Methods">
    /**
     * Executes a stored procedure with the specified parameters.
     *
     * @param sprocName The name of the stored procedure to execute.
     * @param parameters A zero-based indexed list of the parameters to pass
     * into the stored procedure. The key is the order the parameters should be
     * used. The key's value is the parameter data.
     * @param returnsResultSet True = a result set is expected from the sproc;
     * False = no result set will be returned.
     * @return The ResultSet returned from the stored procedure. This will be
     * NULL if there are no result sets.
     * @throws SQLException
     * @throws Exception
     */
    protected ResultSet execSproc(String sprocName,
            HashMap<Integer, SprocParameter> parameters, boolean returnsResultSet)
            throws SQLException, Exception {
        ResultSet results = null;

        String sql = buildSprocSyntax(sprocName, parameters.size(), returnsResultSet);
        try (Connection myConn = sqlConn.getConnection();
                CallableStatement stmt = myConn.prepareCall(sql)) {

            //Set parameter values.
            final ArrayList<Integer> outParams = new ArrayList<>(); //index of OUT parameters
            ParameterMetaData params = stmt.getParameterMetaData();
            for (int i = 0; i < params.getParameterCount(); i++) {
                //Register the OUT parameters.
                if (params.getParameterMode(i) == ParameterMetaData.parameterModeOut
                        || params.getParameterMode(i) == ParameterMetaData.parameterModeInOut) {
                    outParams.add(i);
                    //Must use Scale if numeric or decimal data type.
                    if (params.getParameterType(i) == Types.NUMERIC
                            || params.getParameterType(i) == Types.DECIMAL) {
                        stmt.registerOutParameter(i, params.getParameterType(i), params.getScale(i));
                    } else {
                        stmt.registerOutParameter(i, params.getParameterType(i));
                    }
                }

                SprocParameter.setStatement(stmt, parameters.get(i));
            }

            //Get the result set, if any.
            if (stmt.execute()) {
                results = stmt.getResultSet();
            }

            //Retrieve OUT parameters.
            for (int aParamIndex : outParams) {
                SprocParameter aParam = parameters.get(aParamIndex);
                aParam.setValue(stmt.getString(aParamIndex));
            }
        }

        return results;
    }

    /**
     * Checks to see if any column that is not nullable has a null value.
     *
     * @param columnName The column to check.
     * @param aValue The value to check.
     * @return True = either column allows nulls or value is not null; False =
     * column doesn't allow nulls and value is null.
     */
    protected boolean tryNullCheck(String columnName, java.util.Date aValue) {
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
    protected abstract java.util.Date doNullCheck(String columnName, java.util.Date aValue)
            throws SQLException;/* {
        if (aValue == null && false) {
            throw new NonNullableValueException();
        } else {
            return aValue;
        }
    }*/

    /**
     * Checks to see if any column that is not nullable has a null value.
     *
     * @param columnName The column to check.
     * @param aValue The value to check.
     * @return True = either column allows nulls or value is not null; False =
     * column doesn't allow nulls and value is null.
     */
    protected boolean tryNullCheck(String columnName, java.math.BigDecimal aValue) {
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
    protected abstract java.math.BigDecimal doNullCheck(String columnName, java.math.BigDecimal aValue)
            throws SQLException;/* {
        if (aValue == null && false) {
            throw new NonNullableValueException();
        } else {
            return aValue;
        }
    }*/

    /**
     * Checks to see if any column that is not nullable has a null value.
     *
     * @param columnName The column to check.
     * @param aValue The value to check.
     * @return True = either column allows nulls or value is not null; False =
     * column doesn't allow nulls and value is null.
     */
    protected boolean tryNullCheck(String columnName, Integer aValue) {
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
    protected abstract Integer doNullCheck(String columnName, Integer aValue)
            throws SQLException;/* {
        if (aValue == null && false) {
            throw new NonNullableValueException();
        } else {
            return aValue;
        }
    }*/

    /**
     * Checks to see if any column that is not nullable has a null value.
     *
     * @param columnName The column to check.
     * @param aValue The value to check.
     * @return True = either column allows nulls or value is not null; False =
     * column doesn't allow nulls and value is null.
     */
    protected boolean tryNullCheck(String columnName, String aValue) {
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
    protected abstract String doNullCheck(String columnName, String aValue)
            throws SQLException;/* {
        if (aValue == null && false) {
            throw new NonNullableValueException();
        } else {
            return aValue;
        }
    }*/
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    // </editor-fold>
}
