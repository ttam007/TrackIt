package trackit.DAL;

import trackit.ASupplier;
import java.sql.*;
import java.util.*;

/**
 * DAL Layer: Converts a row in database table Suppliers into a ASupplier object
 * and vice versa.
 *
 * @author Bond
 */
public class SQLHelperSupplier
        extends SQLHelper<ASupplier> {

    // <editor-fold defaultstate="collapsed" desc="Database Columns">
    /**
     *
     */
    public static final String COLUMN_NICKNAME = "nickname";

    /**
     *
     */
    public static final String COLUMN_URL = "url";

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    static {
        COLUMN_PK = "supplierId";
    }

    /**
     * supplier help for mysql
     */
    public SQLHelperSupplier() {

    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

    @Override
    protected ASupplier convertResultSetToObject(ResultSet rs)
            throws SQLException {
        ASupplier anObj = new ASupplier();
        anObj.setPrimaryKey(rs.getInt(COLUMN_PK));
        anObj.setNickname(rs.getString(COLUMN_NICKNAME));
        anObj.setUrl(rs.getString(COLUMN_URL));
        return anObj;
    }

    @Override
    protected ArrayList<ASupplier> execSproc(String sprocName, HashMap<Integer, SprocParameter> parameters)
            throws SQLException, Exception {
        ArrayList<ASupplier> results = new ArrayList<>();

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
    public ArrayList<ASupplier> selectAll()
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();

        ArrayList<ASupplier> results = execSproc("sp_Suppliers_SelectAll", params);
        return results;
    }

    @Override
    public ASupplier selectOne(Integer primaryKey)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(COLUMN_PK, primaryKey.toString(), ParameterDirection.IN));

        ArrayList<ASupplier> results = execSproc("sp_Suppliers_Select", params);
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    @Override
    public Integer insert(ASupplier anObject)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        SprocParameterInteger outParam = new SprocParameterInteger(COLUMN_PK, anObject.getPrimaryKey().toString(), ParameterDirection.OUT);
        params.put(0, outParam);
        params.put(1, new SprocParameterVarchar(COLUMN_NICKNAME, anObject.getNickname(), ParameterDirection.IN));
        params.put(2, new SprocParameterVarchar(COLUMN_URL, anObject.getUrl(), ParameterDirection.IN));

        execSproc("sp_Suppliers_Insert", params);
        Integer primaryKey = Integer.parseInt(outParam.getValue());
        anObject.setPrimaryKey(primaryKey);
        return primaryKey;
    }

    @Override
    public void update(ASupplier anObject)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(COLUMN_PK, anObject.getPrimaryKey().toString(), ParameterDirection.IN));
        params.put(1, new SprocParameterVarchar(COLUMN_NICKNAME, anObject.getNickname(), ParameterDirection.IN));
        params.put(2, new SprocParameterVarchar(COLUMN_URL, anObject.getUrl(), ParameterDirection.IN));

        execSproc("sp_Suppliers_Update", params);
    }

    @Override
    public void delete(Integer primaryKey)
            throws SQLException, Exception {
        HashMap<Integer, SprocParameter> params = new HashMap<>();
        params.put(0, new SprocParameterInteger(COLUMN_PK, primaryKey.toString(), ParameterDirection.IN));

        execSproc("sp_Suppliers_Delete", params);
    }

    @Override
    public java.sql.Date doNullCheck(String columnName, java.sql.Date aValue)
            throws SQLException {
        throw new UnsupportedSQLTypeException(Types.DATE, this.getClass());
    }

    @Override
    public Double doNullCheck(String columnName, Double aValue)
            throws SQLException {
        throw new UnsupportedSQLTypeException(Types.DOUBLE, this.getClass());
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
