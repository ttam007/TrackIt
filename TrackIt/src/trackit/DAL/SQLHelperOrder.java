package trackit.DAL;

import java.sql.*;
import java.util.*;
import trackit.*;

/**
* DAL Layer: Converts a row in database table Orders into an Order object
 and vice versa.
  */
public class SQLHelperOrder
        extends SQLHelper<Order>
        implements ISQLHelper<Order> {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SQLHelperOrder() {
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    @Override
    public ArrayList<Order> selectAll()
            throws SQLException, Exception{
        ArrayList<Order> results = new ArrayList<>();

        HashMap<Integer, SprocParameter> params = new HashMap<>();
        ResultSet rs = super.execSproc("sp_Orders_Select", params);
        while (rs.next()) {
            Order anObj = new Order();
            //Update anObj's properties from the ResultSet.
            results.add(anObj);
        }

        return results;
    }

    @Override
    public Order selectOne(Integer primaryKey)
            throws SQLException {
        return new Order();
    }

    @Override
    public List<Integer> insertAll(List<Order> aList)
            throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public Integer insert(Order anObject)
            throws SQLException {
        Integer primaryKey = INVALID_PRIMARY_KEY;
        return primaryKey;
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
    // </editor-fold>
}
