package trackit.DAL;

import java.sql.*;
import java.util.*;
import trackit.*;

/**
 * DAL Layer: Converts a row in database table InventoryItems into an
 * InventoryItem object and vice versa.
 */
public class SQLHelperInventoryItem
        extends SQLHelper<InventoryItem>
        implements ISQLHelper<InventoryItem> {

    // <editor-fold defaultstate="collapsed" desc="Database Columns">
    public final String COLUMN_NICKNAME = "nickname";
    public final String COLUMN_URL = "URL";

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SQLHelperInventoryItem() {
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    @Override
    public ArrayList<InventoryItem> selectAll()
            throws SQLException, Exception {
        ArrayList<InventoryItem> results = new ArrayList<>();

        HashMap<Integer, SprocParameter> params = new HashMap<>();
        ResultSet rs = super.execSproc("sp_Inventorys_Select", params, true);
        while (rs != null && rs.next()) {
            InventoryItem anObj = new InventoryItem();
            //Update anObj's properties from the ResultSet.
            results.add(anObj);
        }

        return results;
    }

    @Override
    public InventoryItem selectOne(Integer primaryKey)
            throws SQLException, Exception {
        return new InventoryItem();
    }

    @Override
    public List<Integer> insertAll(List<InventoryItem> aList)
            throws SQLException, Exception {
        return new ArrayList<>();
    }

    @Override
    public Integer insert(InventoryItem anObject)
            throws SQLException, Exception {
        Integer primaryKey = INVALID_PRIMARY_KEY;
        return primaryKey;
    }

    @Override
    public void updateAll(List<InventoryItem> aList)
            throws SQLException, Exception {
    }

    @Override
    public void update(InventoryItem anObject)
            throws SQLException, Exception {
    }

    @Override
    public void deleteAll(List<Integer> primaryKeys)
            throws SQLException, Exception {
    }

    @Override
    public void delete(Integer primaryKey)
            throws SQLException, Exception {
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
