package trackit.DAL;

import java.sql.*;
import java.util.*;
import trackit.*;

/**
 * DAL Layer: Converts a row in database table OrderItems into an OrderItem
 * object and vice versa.
 */
public class SQLHelperOrderItem
        extends SQLHelper<OrderItem>
        implements ISQLHelper<OrderItem> {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
 
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SQLHelperOrderItem() {
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    @Override
    public ArrayList<OrderItem> selectAll()
            throws SQLException {
        ArrayList<OrderItem> results = new ArrayList<>();

        HashMap<Integer, String> params = new HashMap<>();
        ResultSet rs = super.execSproc("sp_Orders_Select", params);
        while (rs.next()) {
            OrderItem anObj = new OrderItem();
            //Update anObj's properties from the ResultSet.
            results.add(anObj);
        }

        return results;
    }

    @Override
    public OrderItem selectOne(Integer primaryKey)
            throws SQLException {
        return new OrderItem();
    }

    @Override
    public List<Integer> insertAll(List<OrderItem> aList)
            throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public Integer insert(OrderItem anObject)
            throws SQLException {
        Integer primaryKey = INVALID_PRIMARY_KEY;
        return primaryKey;
    }

    @Override
    public void updateAll(List<OrderItem> aList)
            throws SQLException {
    }

    @Override
    public void update(OrderItem anObject)
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
