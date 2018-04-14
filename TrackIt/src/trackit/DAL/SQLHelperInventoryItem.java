package trackit.DAL;

import java.sql.*;
import java.util.*;

/**
 * DAL Layer: Converts a row in database table InventoryItems into an
 * AnInventoryItem object and vice versa.
 *
 * @author Bond
 */
public class SQLHelperInventoryItem
        extends SQLHelper<AnInventoryItem> {

    // <editor-fold defaultstate="collapsed" desc="Database Columns">
    public static final String COLUMN_ITEMID = "itemId";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_EXPIRATIONDATE = "expirationDate";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_SKU = "sku";
    public static final String COLUMN_SIZEUNIT = "sizeUnit";
    public static final String COLUMN_ITEMSTATUS = "itemStatus";

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    static {
        SQLHelperInventoryItem.COLUMN_PK = "inventoryItemId";
    }

    public SQLHelperInventoryItem() {

    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

    @Override
    protected AnInventoryItem convertResultSetToObject(ResultSet rs)
            throws SQLException {
        AnInventoryItem anObj = new AnInventoryItem();
        anObj.setPrimaryKey(rs.getInt(COLUMN_PK));
        //TODO
        //anObj.setItemId(rs.getString(COLUMN_ITEMID));
        //anObj.setQuantity(rs.getString(COLUMN_QUANTITY));
        //anObj.setExpirationDate(rs.getString(COLUMN_EXPIRATIONDATE));
        anObj.setDescription(rs.getString(COLUMN_DESCRIPTION));
        anObj.setSku(rs.getString(COLUMN_SKU));
        anObj.setSizeUnit(rs.getString(COLUMN_SIZEUNIT));
        anObj.setItemStatus(rs.getString(COLUMN_ITEMSTATUS));
        return anObj;
    }

    @Override
    protected ArrayList<AnInventoryItem> execSproc(String sprocName, HashMap<Integer, SprocParameter> parameters)
            throws SQLException, Exception {
        ArrayList<AnInventoryItem> results = new ArrayList<>();

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
    public ArrayList<AnInventoryItem> selectAll()
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();

        ArrayList<AnInventoryItem> results = execSproc("sp_InventoryItems_SelectAll", params);
        return results;
    }

    @Override
    public AnInventoryItem selectOne(Integer primaryKey)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger("COLUMN_PK", primaryKey.toString(), ParameterDirection.IN));

        ArrayList<AnInventoryItem> results = execSproc("sp_InventoryItems_Select", params);
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    @Override
    public Integer insert(AnInventoryItem anObject)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        SprocParameterInteger outParam = new SprocParameterInteger(COLUMN_PK, anObject.getPrimaryKey().toString(), ParameterDirection.OUT);
        params.put(0, outParam);
        //TODO:
        //params.put(1, new SprocParameterVarchar("nickname", anObject.getNickname(), ParameterDirection.IN));
        //params.put(2, new SprocParameterVarchar("url", anObject.getUrl(), ParameterDirection.IN));

        execSproc("sp_InventoryItems_Insert", params);
        Integer primaryKey = Integer.parseInt(outParam.getValue());
        anObject.setPrimaryKey(primaryKey);
        return primaryKey;
    }

    @Override
    public void update(AnInventoryItem anObject)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(COLUMN_PK, anObject.getPrimaryKey().toString(), ParameterDirection.IN));
        //TODO:
        //params.put(1, new SprocParameterVarchar("nickname", anObject.getNickname(), ParameterDirection.IN));
        //params.put(2, new SprocParameterVarchar("url", anObject.getUrl(), ParameterDirection.IN));

        execSproc("sp_InventoryItems_Update", params);
    }

    @Override
    public void delete(Integer primaryKey)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(COLUMN_PK, primaryKey.toString(), ParameterDirection.IN));

        execSproc("sp_InventoryItems_Delete", params);
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
        //TODO
        if (aValue == null && false /*columnName.equalsIgnoreCase(COLUMN_NICKNAME)*/) {
            throw new NonNullableValueException();
        } else {
            return aValue;
        }
    }
    // </editor-fold>
}
