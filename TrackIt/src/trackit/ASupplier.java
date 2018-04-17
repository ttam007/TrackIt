package trackit;

import java.sql.*;
import java.util.*;
import trackit.DAL.SQLHelper;
import trackit.DAL.SQLHelperSupplier;

/**
 * DAL Layer: Handles all aspects of a single Supplier.
 *
 * @author Bond
 */
public class ASupplier
        extends DatabaseObject {

    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private static final SQLHelperSupplier HELPER = new SQLHelperSupplier();
    private String nickname = null;
    private String url = null;

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">
    /**
     * A supplier entry
     */
    public ASupplier() {
        this.primaryKey = SQLHelper.INVALID_PRIMARY_KEY;
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Setters & Getters">
    @Override
    public void setPrimaryKey(Integer aPrimaryKey)
            throws SQLException {
        this.primaryKey = HELPER.doNullCheck(SQLHelperSupplier.COLUMN_PK, aPrimaryKey);
    }

    /**
     * This can not be null.
     *
     * @param aNickname
     * @throws SQLException
     */
    public void setNickname(String aNickname)
            throws SQLException {
        this.nickname = HELPER.doNullCheck(SQLHelperSupplier.COLUMN_NICKNAME, aNickname);
    }

    /**
     * This can not be null.
     *
     * @return
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * This can be null.
     *
     * @param aURL
     * @throws SQLException
     */
    public void setUrl(String aURL)
            throws SQLException {
        this.url = HELPER.doNullCheck(SQLHelperSupplier.COLUMN_URL, aURL);
    }

    /**
     * This can be null.
     *
     * @return
     */
    public String getUrl() {
        return this.url;
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Public Static Methods">
    /**
     * Gets all the objects from the database.
     *
     * @return A list of objects with values loaded from the database.
     * @throws SQLException
     * @throws Exception
     */
    public static ArrayList<ASupplier> loadAll()
            throws SQLException, Exception {
        return HELPER.selectAll();
    }

    /**
     * Gets the object from the database.
     *
     * @param primaryKey The primary key in the table to retrieve.
     * @return The object with values loaded from the database, or a null object
     * if not found.
     * @throws SQLException
     * @throws Exception
     */
    public static ASupplier load(int primaryKey)
            throws SQLException, Exception {
        return HELPER.selectOne(primaryKey);
    }

    /**
     * Saves the specified object to the database.
     *
     * @param anObj The object to be saved.
     * @throws SQLException
     * @throws Exception
     */
    public static void save(ASupplier anObj)
            throws SQLException, Exception {
        if (anObj.isAlreadyInDatabase()) {
            HELPER.update(anObj);
        } else {
            HELPER.insert(anObj);
        }
    }

    /**
     * Removes the specific object from the database.
     *
     * @param anObj The object to be saved.
     * @throws SQLException
     * @throws Exception
     */
    public static void remove(ASupplier anObj)
            throws SQLException, Exception {
        remove(anObj.getPrimaryKey());
    }

    /**
     * Removes the specific primaryKey from the database.
     *
     * @param primaryKey The primary key to be deleted.
     * @throws SQLException
     * @throws Exception
     */
    public static void remove(Integer primaryKey)
            throws SQLException, Exception {
        HELPER.delete(primaryKey);
    }
    // </editor-fold>
}
