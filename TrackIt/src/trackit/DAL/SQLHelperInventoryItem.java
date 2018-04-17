package trackit.DAL;

import trackit.AnInventoryItem;
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

    // <editor-fold defaultstate="collapsed" desc="Constants">
    private static final SQLHelperItem HELPER_ITEM = new SQLHelperItem();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Database Columns">
    /**
     *
     */
    public static final String COLUMN_ITEMID = "itemId";

    /**
     *
     */
    public static final String COLUMN_QUANTITY = "quantity";

    /**
     *
     */
    public static final String COLUMN_EXPIRATIONDATE = "expirationDate";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    static {
        COLUMN_PK = "inventoryItemId";
    }

    /**
     * Default constructor.
     */
    public SQLHelperInventoryItem() {

    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    @Override
    protected AnInventoryItem convertResultSetToObject(ResultSet rs)
            throws SQLException {
        AnInventoryItem anObj = new AnInventoryItem();
        anObj.setPrimaryKey(rs.getInt(SQLHelperInventoryItem.COLUMN_PK));
        anObj.setItemId(rs.getInt(COLUMN_ITEMID));
        anObj.setQuantity(rs.getInt(COLUMN_QUANTITY));
        anObj.setExpirationDate(rs.getDate(COLUMN_EXPIRATIONDATE));
        anObj.setDescription(rs.getString(SQLHelperItem.COLUMN_DESCRIPTION));
        anObj.setSku(rs.getString(SQLHelperItem.COLUMN_SKU));
        anObj.setSizeUnit(rs.getString(SQLHelperItem.COLUMN_SIZEUNIT));
        anObj.setItemStatus(rs.getString(SQLHelperItem.COLUMN_ITEMSTATUS));
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
        params.put(0, new SprocParameterInteger(SQLHelperInventoryItem.COLUMN_PK, primaryKey.toString(), ParameterDirection.IN));

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
        SprocParameterInteger outParam = new SprocParameterInteger(SQLHelperInventoryItem.COLUMN_PK, anObject.getPrimaryKey().toString(), ParameterDirection.OUT);
        params.put(0, outParam);
        params.put(1, new SprocParameterInteger(SQLHelperInventoryItem.COLUMN_QUANTITY, anObject.getQuantity().toString(), ParameterDirection.IN));
        String expirationDate = (anObject.getExpirationDate() == null ? null : anObject.getExpirationDate().toString());
        params.put(2, new SprocParameterDate(SQLHelperInventoryItem.COLUMN_EXPIRATIONDATE, expirationDate, ParameterDirection.IN));
        params.put(3, new SprocParameterVarchar(SQLHelperItem.COLUMN_DESCRIPTION, anObject.getDescription(), ParameterDirection.IN));
        params.put(4, new SprocParameterVarchar(SQLHelperItem.COLUMN_SKU, anObject.getSku(), ParameterDirection.IN));
        params.put(5, new SprocParameterVarchar(SQLHelperItem.COLUMN_SIZEUNIT, anObject.getSizeUnit(), ParameterDirection.IN));
        params.put(6, new SprocParameterVarchar(SQLHelperItem.COLUMN_ITEMSTATUS, anObject.getItemStatus().getText(), ParameterDirection.IN));

        execSproc("sp_InventoryItems_Insert", params);
        Integer primaryKey = Integer.parseInt(outParam.getValue());
        anObject.setPrimaryKey(primaryKey);
        return primaryKey;
    }

    @Override
    public void update(AnInventoryItem anObject)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(SQLHelperInventoryItem.COLUMN_PK, anObject.getPrimaryKey().toString(), ParameterDirection.IN));
        params.put(1, new SprocParameterInteger(SQLHelperInventoryItem.COLUMN_QUANTITY, anObject.getQuantity().toString(), ParameterDirection.IN));
        String expirationDate = (anObject.getExpirationDate() == null ? null : anObject.getExpirationDate().toString());
        params.put(2, new SprocParameterDate(SQLHelperInventoryItem.COLUMN_EXPIRATIONDATE, expirationDate, ParameterDirection.IN));
        params.put(3, new SprocParameterVarchar(SQLHelperItem.COLUMN_DESCRIPTION, anObject.getDescription(), ParameterDirection.IN));
        params.put(4, new SprocParameterVarchar(SQLHelperItem.COLUMN_SKU, anObject.getSku(), ParameterDirection.IN));
        params.put(5, new SprocParameterVarchar(SQLHelperItem.COLUMN_SIZEUNIT, anObject.getSizeUnit(), ParameterDirection.IN));
        params.put(6, new SprocParameterVarchar(SQLHelperItem.COLUMN_ITEMSTATUS, anObject.getItemStatus().getText(), ParameterDirection.IN));

        execSproc("sp_InventoryItems_Update", params);
    }

    @Override
    public void delete(Integer primaryKey)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(SQLHelperInventoryItem.COLUMN_PK, primaryKey.toString(), ParameterDirection.IN));

        execSproc("sp_InventoryItems_Delete", params);
    }

    @Override
    public java.sql.Date doNullCheck(String columnName, java.sql.Date aValue)
            throws SQLException {
        if (aValue == null && columnName.equalsIgnoreCase(COLUMN_EXPIRATIONDATE)) {
            return aValue;
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
                || columnName.equalsIgnoreCase(COLUMN_ITEMID)
                || columnName.equalsIgnoreCase(COLUMN_QUANTITY))) {
            throw new NonNullableValueException();
        } else {
            return aValue;
        }
    }

    @Override
    public String doNullCheck(String columnName, String aValue)
            throws SQLException {
        return HELPER_ITEM.doNullCheck(columnName, aValue);
    }
    // </editor-fold>
}
