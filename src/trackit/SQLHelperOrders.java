package trackit;

import java.sql.*;
import java.util.*;

/**
 * Handles all the CRUD operations for Orders.
 */
public class SQLHelperOrders
        extends SQLHelper<Order>
        implements ISQLHelper<Order> {

    /**
     *
     * @return @throws SQLException
     */
    @Override
    public ArrayList<Order> selectAll()
            throws SQLException {
        ArrayList<Order> results = new ArrayList<>();

        HashMap<Integer, String> params = new HashMap<>();
        ResultSet rs = super.execSproc("sp_Orders_Select", params);
        while (rs.next()) {
            Order anObj = new Order();
            //Populate anObj properties from rs.
            results.add(anObj);
        }

        return results;
    }

    @Override
    public ArrayList<Order> selectOne(Integer primaryKey)
            throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public void insertAll(List<Order> aList)
            throws SQLException {

    }

    @Override
    public void insert(Order anObject)
            throws SQLException {

    }

    @Override
    public void updateAll(List<Order> aList)
            throws SQLException {

    }

    @Override
    public void update(Order anObject)
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
