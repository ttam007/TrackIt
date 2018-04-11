package trackit.DAL;

import java.sql.*;
import java.util.*;
import trackit.*;

/**
 * DAL Layer: Converts a row in database table InventoryItems into an
 * InventoryItem object and vice versa.
 *
 * @author Bond
 */
public class SQLHelperInventoryItem
        extends SQLHelper<InventoryItem>
        implements ISQLHelper<InventoryItem> {

    // <editor-fold defaultstate="collapsed" desc="Database Columns">
    public final String COLUMN_ITEMID = "itemId";
    public final String COLUMN_QUANTITY = "quantity";
    public final String COLUMN_EXPIRATIONDATE = "expirationDate";
    public final String COLUMN_DESCRIPTION = "description";
    public final String COLUMN_SKU = "sku";
    public final String COLUMN_SIZEUNIT = "sizeUnit";
    public final String COLUMN_ITEMSTATUS = "itemStatus";

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SQLHelperInventoryItem() {
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

    @Override
    protected InventoryItem convertResultSetToObject(ResultSet rs)
            throws SQLException {
        InventoryItem anObj = new InventoryItem();
        anObj.setPrimaryKey(rs.getInt(COLUMN_PK));
        //TODO:
        //anObj.setNickname(rs.getString(COLUMN_NICKNAME));
        //anObj.setUrl(rs.getString(COLUMN_URL));
        return anObj;
    }

    @Override
    protected ArrayList<InventoryItem> execSproc(String sprocName, HashMap<Integer, SprocParameter> parameters)
            throws SQLException, Exception {
        ArrayList<InventoryItem> results = new ArrayList<>();

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
    public ArrayList<InventoryItem> selectAll()
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();

        ArrayList<InventoryItem> results = execSproc("sp_InventoryItems_SelectAll", params);
        return results;
    }

    @Override
    public InventoryItem selectOne(Integer primaryKey)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger("COLUMN_PK", primaryKey.toString(), ParameterDirection.IN));

        ArrayList<InventoryItem> results = execSproc("sp_InventoryItems_Select", params);
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    @Override
    public List<Integer> insertAll(List<InventoryItem> aList)
            throws SQLException, Exception {
        ArrayList<Integer> primaryKeys = new ArrayList<>();
        for (InventoryItem anObj : aList) {
            primaryKeys.add(insert(anObj));
        }
        return primaryKeys;
    }

    @Override
    public Integer insert(InventoryItem anObject)
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
    public void updateAll(List<InventoryItem> aList)
            throws SQLException, Exception {
        for (InventoryItem anObj : aList) {
            update(anObj);
        }
    }

    @Override
    public void update(InventoryItem anObject)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(COLUMN_PK, anObject.getPrimaryKey().toString(), ParameterDirection.IN));
        //TODO:
        //params.put(1, new SprocParameterVarchar("nickname", anObject.getNickname(), ParameterDirection.IN));
        //params.put(2, new SprocParameterVarchar("url", anObject.getUrl(), ParameterDirection.IN));

        execSproc("sp_InventoryItems_Update", params);
    }

    @Override
    public void deleteAll(List<Integer> primaryKeys)
            throws SQLException, Exception {
        for (Integer aPK : primaryKeys) {
            delete(aPK);
        }
    }

    @Override
    public void delete(Integer primaryKey)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(COLUMN_PK, primaryKey.toString(), ParameterDirection.IN));

        execSproc("sp_InventoryItems_Delete", params);
    }

    @Override
    public java.util.Date doNullCheck(String columnName, java.util.Date aValue)
            throws SQLException {
        throw new NonNullableValueException();
    }

    @Override
    public Double doNullCheck(String columnName, Double aValue)
            throws SQLException {
        throw new NonNullableValueException();
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
        if (aValue == null && false /*columnName.equalsIgnoreCase(COLUMN_NICKNAME)*/) {
            throw new NonNullableValueException();
        } else {
            return aValue;
        }
    }
    // </editor-fold>
}
