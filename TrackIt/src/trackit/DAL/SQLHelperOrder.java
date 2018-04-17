package trackit.DAL;

import trackit.AnOrder;
import java.sql.*;
import java.util.*;

/**
 * DAL Layer: Converts a row in database table Orders into an AnOrder object and
 * vice versa.
 *
 * @author Bond
 */
public class SQLHelperOrder
        extends SQLHelper<AnOrder> {

    // <editor-fold defaultstate="collapsed" desc="Database Columns">
    /**
     *
     */
    public static final String COLUMN_DESCRIPTION = "description";

    /**
     *
     */
    public static final String COLUMN_ORDEREDFROM = "orderedFrom";

    /**
     *
     */
    public static final String COLUMN_ORDERSTATUS = "orderStatus";

    /**
     *
     */
    public static final String COLUMN_DATEORDERED = "dateOrdered";

    /**
     *
     */
    public static final String COLUMN_DATEEXPECTED = "dateExpected";

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    static {
        COLUMN_PK = "orderId";
    }

    /**
     * sql help order for dbase
     */
    public SQLHelperOrder() {

    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

    @Override
    protected AnOrder convertResultSetToObject(ResultSet rs)
            throws SQLException {
        AnOrder anObj = new AnOrder();
        anObj.setPrimaryKey(rs.getInt(COLUMN_PK));
        anObj.setDescription(rs.getString(COLUMN_DESCRIPTION));
        anObj.setOrderedFrom(rs.getInt(COLUMN_ORDEREDFROM));
        anObj.setOrderStatus(rs.getString(COLUMN_ORDERSTATUS));
        anObj.setDateOrdered(rs.getDate(COLUMN_DATEORDERED));
        anObj.setDateExpected(rs.getDate(COLUMN_DATEEXPECTED));
        return anObj;
    }

    @Override
    protected ArrayList<AnOrder> execSproc(String sprocName, HashMap<Integer, SprocParameter> parameters)
            throws SQLException, Exception {
        ArrayList<AnOrder> results = new ArrayList<>();

        String sql = buildSprocSyntax(sprocName, parameters.size());
        System.out.println("execSproc's sql = " + sql);

        try (Connection myConn = sqlConn.getConnection();
                CallableStatement stmt = myConn.prepareCall(sql)) {

            final ArrayList<Integer> outParams = setParameters(stmt, parameters);

            //Get the result set, if any, and add to results (returned variable).
            if (stmt.execute()) {
                ResultSet rs = stmt.getResultSet();
                while (rs.next()) {
                    results.add(convertResultSetToObject(rs));
                }
            }

            //Retrieve OUT parameters.
            for (int aParamIndex : outParams) {
                SprocParameter aParam = parameters.get(aParamIndex);
                aParam.setValue(getOutParameterValue(stmt, aParam));
            }
        }

        return results;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    @Override
    public ArrayList<AnOrder> selectAll()
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();

        ArrayList<AnOrder> results = execSproc("sp_Orders_SelectAll", params);
        return results;
    }

    @Override
    public AnOrder selectOne(Integer primaryKey)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(COLUMN_PK, primaryKey.toString(), ParameterDirection.IN));

        ArrayList<AnOrder> results = execSproc("sp_Orders_Select", params);
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    @Override
    public Integer insert(AnOrder anObject)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        SprocParameterInteger outParam = new SprocParameterInteger(COLUMN_PK, anObject.getPrimaryKey().toString(), ParameterDirection.OUT);
        params.put(0, outParam);
        params.put(1, new SprocParameterVarchar(COLUMN_DESCRIPTION, anObject.getDescription(), ParameterDirection.IN));
        params.put(2, new SprocParameterInteger(COLUMN_ORDEREDFROM, anObject.getOrderedFrom().toString(), ParameterDirection.IN));
        params.put(3, new SprocParameterVarchar(COLUMN_ORDERSTATUS, anObject.getOrderStatus().getText(), ParameterDirection.IN));
        params.put(4, new SprocParameterDate(COLUMN_DATEORDERED, anObject.getDateOrdered().toString(), ParameterDirection.IN));
        String dateExpected = (anObject.getDateExpected() == null ? null : anObject.getDateExpected().toString());
        params.put(5, new SprocParameterDate(COLUMN_DATEEXPECTED, dateExpected, ParameterDirection.IN));

        execSproc("sp_Orders_Insert", params);
        Integer primaryKey = Integer.parseInt(outParam.getValue());
        anObject.setPrimaryKey(primaryKey);
        return primaryKey;
    }

    @Override
    public void update(AnOrder anObject)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(COLUMN_PK, anObject.getPrimaryKey().toString(), ParameterDirection.IN));
        params.put(1, new SprocParameterVarchar(COLUMN_DESCRIPTION, anObject.getDescription(), ParameterDirection.IN));
        params.put(2, new SprocParameterInteger(COLUMN_ORDEREDFROM, anObject.getOrderedFrom().toString(), ParameterDirection.IN));
        params.put(3, new SprocParameterVarchar(COLUMN_ORDERSTATUS, anObject.getOrderStatus().getText(), ParameterDirection.IN));
        params.put(4, new SprocParameterDate(COLUMN_DATEORDERED, anObject.getDateOrdered().toString(), ParameterDirection.IN));
        String dateExpected = (anObject.getDateExpected() == null ? null : anObject.getDateExpected().toString());
        params.put(5, new SprocParameterDate(COLUMN_DATEEXPECTED, dateExpected, ParameterDirection.IN));

        execSproc("sp_Orders_Update", params);
    }

    @Override
    public void delete(Integer primaryKey)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(COLUMN_PK, primaryKey.toString(), ParameterDirection.IN));

        execSproc("sp_Orders_Delete", params);
    }

    @Override
    public java.sql.Date doNullCheck(String columnName, java.sql.Date aValue)
            throws SQLException {
        if (aValue == null && columnName.equalsIgnoreCase(COLUMN_DATEORDERED)) {
            throw new NonNullableValueException();
        } else {
            return aValue;
        }
    }

    @Override
    public Double doNullCheck(String columnName, Double aValue)
            throws SQLException {
        throw new UnsupportedSQLTypeException(Types.DOUBLE, this.getClass());
    }

    @Override
    public Integer doNullCheck(String columnName, Integer aValue)
            throws SQLException {
        if (aValue == null
                && (columnName.equalsIgnoreCase(COLUMN_PK)
                || columnName.equalsIgnoreCase(COLUMN_ORDEREDFROM))) {
            throw new NonNullableValueException();
        } else {
            return aValue;
        }
    }

    @Override
    public String doNullCheck(String columnName, String aValue)
            throws SQLException {
        if (aValue == null
                && (columnName.equalsIgnoreCase(COLUMN_DESCRIPTION)
                || columnName.equalsIgnoreCase(COLUMN_ORDERSTATUS))) {
            throw new NonNullableValueException();
        } else {
            return aValue;
        }
    }
    // </editor-fold>
}
