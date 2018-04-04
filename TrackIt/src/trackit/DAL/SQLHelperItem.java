package trackit.DAL;

import java.sql.*;
import java.util.*;
import trackit.*;

/**
 * DAL Layer: Converts a row in database table Items into an Item object
 * and vice versa.
 */
public class SQLHelperItem
        extends SQLHelper<Item>
        implements ISQLHelper<Item> {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
   // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SQLHelperItem() {
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    @Override
    public ArrayList<Item> selectAll()
            throws SQLException {
        ArrayList<Item> results = new ArrayList<>();

        HashMap<Integer, String> params = new HashMap<>();
        ResultSet rs = super.execSproc("sp_Items_Select", params);
        while (rs.next()) {
            Item anObj = new Item();
            //Update anObj's properties from the ResultSet.
            results.add(anObj);
        }

        return results;
    }

    @Override
    public Item selectOne(Integer primaryKey)
            throws SQLException {
        return new Item();
    }

    @Override
    public List<Integer> insertAll(List<Item> aList)
            throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public Integer insert(Item anObject)
            throws SQLException {
        Integer primaryKey = INVALID_PRIMARY_KEY;
        return primaryKey;
    }

    @Override
    public void updateAll(List<Item> aList)
            throws SQLException {
    }

    @Override
    public void update(Item anObject)
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
