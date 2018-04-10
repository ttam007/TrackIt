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

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SQLHelperSupplier() {
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    @Override
    public ArrayList<Supplier> selectAll()
            throws SQLException, Exception{
        ArrayList<Supplier> results = new ArrayList<>();

        HashMap<Integer, SprocParameter> params = new HashMap<>();
        ResultSet rs = super.execSproc("sp_Suppliers_Select", params);
        while (rs.next()) {
            Supplier anObj = new Supplier();
            //Update anObj's properties from the ResultSet.
            results.add(anObj);
        }

        return results;
    }

    @Override
    public Supplier selectOne(Integer primaryKey)
            throws SQLException {
        return new Supplier();
    }

    @Override
    public List<Integer> insertAll(List<Supplier> aList)
            throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public Integer insert(Supplier anObject)
            throws SQLException {
        Integer primaryKey = INVALID_PRIMARY_KEY;
        return primaryKey;
    }

    @Override
    public void updateAll(List<Supplier> aList)
            throws SQLException {
    }

    @Override
    public void update(Supplier anObject)
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
