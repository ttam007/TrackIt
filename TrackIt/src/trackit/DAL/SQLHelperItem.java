package trackit.DAL;

import java.sql.*;
import java.util.*;

/**
 * DAL Layer: Converts a row in database table Items into an AnItem object and
 * vice versa.
 *
 * @author Bond
 */
public class SQLHelperItem
        extends SQLHelper<AnItem> {

    // <editor-fold defaultstate="collapsed" desc="Database Columns">
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_SKU = "sku";
    public static final String COLUMN_SIZEUNIT = "sizeUnit";
    public static final String COLUMN_ITEMSTATUS = "itemStatus";

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    static {
        COLUMN_PK = "supplierId";
    }

    public SQLHelperItem() {

    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

    @Override
    protected AnItem convertResultSetToObject(ResultSet rs)
            throws SQLException {
        /*AnItem anObj = new AnItem();
        anObj.setPrimaryKey(rs.getInt(COLUMN_PK));
        anObj.setDescription(rs.getString(COLUMN_DESCRIPTION));
        anObj.setSku(rs.getString(COLUMN_SKU));
        anObj.setSizeUNit(rs.getString(COLUMN_SIZEUNIT));
        anObj.setItemStatus(rs.getString(COLUMN_ITEMSTATUS));
        return anObj;*/
        return null;
    }

    @Override
    protected ArrayList<AnItem> execSproc(String sprocName, HashMap<Integer, SprocParameter> parameters)
            throws SQLException, Exception {
        ArrayList<AnItem> results = new ArrayList<>();

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
    public ArrayList<AnItem> selectAll()
            throws SQLException, Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public AnItem selectOne(Integer primaryKey)
            throws SQLException, Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer insert(AnItem anObject)
            throws SQLException, Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(AnItem anObject)
            throws SQLException, Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer primaryKey)
            throws SQLException, Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public java.sql.Date doNullCheck(String columnName, java.sql.Date aValue)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Double doNullCheck(String columnName, Double aValue)
            throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer doNullCheck(String columnName, Integer aValue)
            throws SQLException {
        if (aValue == null && columnName.equalsIgnoreCase(COLUMN_PK)) {
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
                || columnName.equalsIgnoreCase(COLUMN_SKU)
                || columnName.equalsIgnoreCase(COLUMN_SIZEUNIT)
                || columnName.equalsIgnoreCase(COLUMN_ITEMSTATUS))) {
            throw new NonNullableValueException();
        } else {
            return aValue;
        }
    }
    // </editor-fold>
}
