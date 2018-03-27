package trackit.DAL;

import java.sql.*;
import java.util.*;
import trackit.Item;

/**
 * Handles all the CRUD operations for Items.
 */
public class SQLHelperItems
        extends SQLHelper<Item>
        implements ISQLHelper<Item> {

    /**
     *
     * @return @throws SQLException
     */
    @Override
    public ArrayList<Item> selectAll()
            throws SQLException {
        ArrayList<Item> results = new ArrayList<>();

        HashMap<Integer, String> params = new HashMap<>();
        ResultSet rs = super.execSproc("sp_Items_Select", params);
        while (rs.next()) {
            Item anObj = new Item();
            //Populate anObj properties from rs.
            results.add(anObj);
        }

        return results;
    }

    @Override
    public ArrayList<Item> selectOne(Integer primaryKey)
            throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public void insertAll(List<Item> aList)
            throws SQLException {

    }

    @Override
    public void insert(Item anObject)
            throws SQLException {

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
}
