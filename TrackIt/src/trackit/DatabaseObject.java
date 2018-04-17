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
     *
     */
    protected Integer primaryKey = null;
    private String errorMessage;

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
     *
     * @param errorMessage
     */
    protected void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * The getter for the errorMessage field. Will be either cleared or set with
     * an error message when a method from the IDataAwareObject interface is
     * called. The error message is only valid for the last interface method
     * call.
     *
     * @return
     */
    protected String getErrorMessage() {
        return this.errorMessage;
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
