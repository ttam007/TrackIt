package trackit.DAL;

import java.sql.*;
import java.util.*;
import trackit.*;

/**
 * DAL Layer: Converts a row in database table Suppliers into a Supplier object
 * and vice versa.
 */
public class SQLHelperSupplier
        extends SQLHelper<Supplier>
        implements ISQLHelper<Supplier> {

    // <editor-fold defaultstate="collapsed" desc="Database Columns">
    public final String COLUMN_NICKNAME = "nickname";
    public final String COLUMN_URL = "URL";

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SQLHelperSupplier() {
        COLUMN_PK = "supplierId";
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

    private Supplier convertResultSetToObject(ResultSet rs)
            throws SQLException {
        Supplier anObj = new Supplier();
        anObj.setNickname(rs.getString(COLUMN_NICKNAME));
        anObj.setUrl(rs.getString(COLUMN_URL));
        return anObj;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    @Override
    public ArrayList<Supplier> selectAll()
            throws SQLException, Exception {
        ArrayList<Supplier> results = new ArrayList<>();

        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger("supplierId", null, ParameterDirection.IN));

        ResultSet rs = super.execSproc("sp_Suppliers_Select", params, true);
        if (rs != null) {
            rs.first();
            while (rs.next()) {
                results.add(convertResultSetToObject(rs));
            }
        }
        return results;
    }

    @Override
    public Supplier selectOne(Integer primaryKey)
            throws SQLException, Exception {
        Supplier results = null;

        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger("supplierId", primaryKey.toString(), ParameterDirection.IN));

        ResultSet rs = super.execSproc("sp_Suppliers_Select", params, true);
        if (rs != null) {
            rs.first();
            while (rs.next()) {
                results = convertResultSetToObject(rs);
            }
        }
        return results;
    }

    @Override
    public List<Integer> insertAll(List<Supplier> aList)
            throws SQLException, Exception {
        ArrayList<Integer> primaryKeys = new ArrayList<>();
        for (Supplier anObj : aList) {
            primaryKeys.add(insert(anObj));
        }
        return primaryKeys;
    }

    @Override
    public Integer insert(Supplier anObject)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger("nickname", anObject.getNickname(), ParameterDirection.IN));
        params.put(1, new SprocParameterInteger("url", anObject.getUrl(), ParameterDirection.IN));
        SprocParameterInteger outParam = new SprocParameterInteger("supplierId", anObject.getPrimaryKey().toString(), ParameterDirection.OUT);
        params.put(2, outParam);

        super.execSproc("sp_Suppliers_Insert", params, false);
        Integer primaryKey = Integer.parseInt(outParam.getValue());
        anObject.setPrimaryKey(primaryKey);
        return primaryKey;
    }

    @Override
    public void updateAll(List<Supplier> aList)
            throws SQLException, Exception {
        for (Supplier anObj : aList) {
            update(anObj);
        }
    }

    @Override
    public void update(Supplier anObject)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger("supplierId", anObject.getPrimaryKey().toString(), ParameterDirection.IN));
        params.put(1, new SprocParameterInteger("nickname", anObject.getNickname(), ParameterDirection.IN));
        params.put(2, new SprocParameterInteger("url", anObject.getUrl(), ParameterDirection.IN));

        super.execSproc("sp_Suppliers_Update", params, false);
    }

    @Override
    public void deleteAll(List<Integer> primaryKeys)
            throws SQLException, Exception {
        throw new SQLException();
    }

    @Override
    public void delete(Integer primaryKey)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger("supplierId", primaryKey.toString(), ParameterDirection.IN));

        super.execSproc("sp_Suppliers_Delete", params, false);
    }

    @Override
    public java.util.Date doNullCheck(String columnName, java.util.Date aValue)
            throws SQLException {
        throw new NonNullableValueException();
    }

    @Override
    public java.math.BigDecimal doNullCheck(String columnName, java.math.BigDecimal aValue)
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
        if (aValue == null && columnName.equalsIgnoreCase(COLUMN_NICKNAME)) {
            throw new NonNullableValueException();
        } else {
            return aValue;
        }
    }
    // </editor-fold>
}
