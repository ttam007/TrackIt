package trackit;

import java.sql.*;
import java.util.*;

/**
 * Handles all the CRUD operations for Suppliers.
 */
public class SQLHelperSuppliers
        extends SQLHelper<Supplier>
        implements ISQLHelper<Supplier> {

    /**
     *
     * @return @throws SQLException
     */
    @Override
    public ArrayList<Supplier> selectAll()
            throws SQLException {
        ArrayList<Supplier> results = new ArrayList<>();

        HashMap<Integer, String> params = new HashMap<>();
        ResultSet rs = super.execSproc("sp_Suppliers_Select", params);
        while (rs.next()) {
            Supplier anObj = new Supplier();
            //Populate anObj properties from rs.
            results.add(anObj);
        }

        return results;
    }

    @Override
    public ArrayList<Supplier> selectOne(Integer primaryKey)
            throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public void insertAll(List<Supplier> aList)
            throws SQLException {

    }

    @Override
    public void insert(Supplier anObject)
            throws SQLException {

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
}
