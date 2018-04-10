package trackit.DAL;

import java.sql.*;
import java.util.*;
import trackit.*;

/**
 * DAL Layer: Converts a row in database table InventoryItems into an InventoryItem
 object and vice versa.
 */
public class SQLHelperInventoryItem
        extends SQLHelper<InventoryItem>
        implements ISQLHelper<InventoryItem> {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
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
        ResultSet rs = super.execSproc("sp_Inventorys_Select", params);
        while (rs.next()) {
            InventoryItem anObj = new InventoryItem();
            //Update anObj's properties from the ResultSet.
            results.add(anObj);
        }

        return results;
    }

    @Override
    public InventoryItem selectOne(Integer primaryKey)
            throws SQLException {
        return new InventoryItem();
    }

    @Override
    public List<Integer> insertAll(List<InventoryItem> aList)
            throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public Integer insert(InventoryItem anObject)
            throws SQLException {
        Integer primaryKey = INVALID_PRIMARY_KEY;
        return primaryKey;
    }

    @Override
    public void updateAll(List<InventoryItem> aList)
            throws SQLException {
    }

    @Override
    public void update(InventoryItem anObject)
            throws SQLException {
    }

    @Override
    public void deleteAll(List<Integer> primaryKeys)
            throws SQLException {
    }

    @Override
    public void delete(Integer primaryKey)
            throws SQLException {
    }
    // </editor-fold>
}
