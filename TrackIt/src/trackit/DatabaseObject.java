package trackit;

import java.sql.SQLException;
import trackit.DAL.SQLHelper;

/**
 * Super class of all objects that exist in the database.
 *
 * @author Bond
 */
public abstract class DatabaseObject {

    // <editor-fold defaultstate="expanded" desc="Protected Fields">
    /**
     * The primary key of the database object.
     */
    protected Integer primaryKey = null;

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Protected Methods">
    /**
     * Determines if this object is already saved to the database or not.
     *
     * @return True = It's already in the database; False = It's not in the
     * database.
     */
    protected boolean isAlreadyInDatabase() {
        return !(primaryKey.equals(SQLHelper.INVALID_PRIMARY_KEY));
    }

    /**
     * This can not be null. Must be overridden in child object to handle null
     * checks.
     *
     * @param primaryKey
     * @throws SQLException
     */
    protected abstract void setPrimaryKey(Integer primaryKey)
            throws SQLException;

    /**
     * This can not be null.
     *
     * @return
     */
    public Integer getPrimaryKey() {
        return this.primaryKey;
    }
    // </editor-fold>  
}
