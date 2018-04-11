package trackit.DAL;

import java.sql.*;
import java.util.*;
import trackit.*;

/**
 * DAL Layer: Converts a row in database table Suppliers into a Supplier object
 * and vice versa.
 *
 * @author Bond
 */
public class SQLHelperSupplier
        extends SQLHelper<Supplier>
        implements ISQLHelper<Supplier> {

    // <editor-fold defaultstate="collapsed" desc="Database Columns">
    public final String COLUMN_NICKNAME = "nickname";
    public final String COLUMN_URL = "url";

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SQLHelperSupplier() {
        COLUMN_PK = "supplierId";
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

    @Override
    protected Supplier convertResultSetToObject(ResultSet rs)
            throws SQLException {
        Supplier anObj = new Supplier();
        anObj.setPrimaryKey(rs.getInt(COLUMN_PK));
        anObj.setNickname(rs.getString(COLUMN_NICKNAME));
        anObj.setUrl(rs.getString(COLUMN_URL));
        return anObj;
    }

    @Override
    protected ArrayList<Supplier> execSproc(String sprocName, HashMap<Integer, SprocParameter> parameters)
            throws SQLException, Exception {
        ArrayList<Supplier> results = new ArrayList<>();

        String sql = buildSprocSyntax(sprocName, parameters.size());
        System.out.println("execSproc's sql = " + sql);

        try (Connection myConn = sqlConn.getConnection();
                CallableStatement stmt = myConn.prepareCall(sql)) {

            final ArrayList<Integer> outParams = setParameters(stmt, parameters);

            //Get the result set, if any, and add to results (returned variable).
            if (stmt.execute()) {
                ResultSet rs = stmt.getResultSet();
                while (rs.next()) {
                    results.add(convertResultSetToObject(rs));
                }
            }

            //Retrieve OUT parameters.
            for (int aParamIndex : outParams) {
                SprocParameter aParam = parameters.get(aParamIndex);
                aParam.setValue(getOutParameterValue(stmt, aParam));
            }
        }

        return results;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    @Override
    public ArrayList<Supplier> selectAll()
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();

        ArrayList<Supplier> results = execSproc("sp_Suppliers_SelectAll", params);
        return results;
    }

    @Override
    public Supplier selectOne(Integer primaryKey)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(COLUMN_PK, primaryKey.toString(), ParameterDirection.IN));

        ArrayList<Supplier> results = execSproc("sp_Suppliers_Select", params);
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
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
        SprocParameterInteger outParam = new SprocParameterInteger(COLUMN_PK, anObject.getPrimaryKey().toString(), ParameterDirection.OUT);
        params.put(0, outParam);
        params.put(1, new SprocParameterVarchar("nickname", anObject.getNickname(), ParameterDirection.IN));
        params.put(2, new SprocParameterVarchar("url", anObject.getUrl(), ParameterDirection.IN));

        execSproc("sp_Suppliers_Insert", params);
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
        params.put(0, new SprocParameterInteger(COLUMN_PK, anObject.getPrimaryKey().toString(), ParameterDirection.IN));
        params.put(1, new SprocParameterVarchar("nickname", anObject.getNickname(), ParameterDirection.IN));
        params.put(2, new SprocParameterVarchar("url", anObject.getUrl(), ParameterDirection.IN));

        execSproc("sp_Suppliers_Update", params);
    }

    @Override
    public void deleteAll(List<Integer> primaryKeys)
            throws SQLException, Exception {
        for (Integer aPK : primaryKeys) {
            delete(aPK);
        }
    }

    @Override
    public void delete(Integer primaryKey)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(COLUMN_PK, primaryKey.toString(), ParameterDirection.IN));

        execSproc("sp_Suppliers_Delete", params);
    }

    @Override
    public java.util.Date doNullCheck(String columnName, java.util.Date aValue)
            throws SQLException {
        throw new NonNullableValueException();
    }

    @Override
    public Double doNullCheck(String columnName, Double aValue)
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
