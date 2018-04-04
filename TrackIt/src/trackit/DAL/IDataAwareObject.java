package trackit.DAL;

import java.sql.*;

/**
 * DAL Layer: All objects that interact with the database should use this
 * interface.
 */
public abstract interface IDataAwareObject {

    /**
     * The getter for the primary key property of this object.
     *
     * @return The primary key. If negative, then not set in database. If zero,
     * then it's not valid. If positive, then it's set in database.
     */
    public abstract Integer getPrimaryKey();

    /**
     * Loads the object from the database into the BAL layer. Must catch
     * IllegalArgumentException & SQLException and handle them by setting
     * errorMessage property.
     *
     * @return True = success; False = failure, so UI layer must call
     * getErrorMessage and display.
     */
    public abstract boolean load();

    /**
     * Loads the object that has the specified primary key from the database
     * into the BAL layer. Must catch IllegalArgumentException & SQLException
     * and handle them by setting errorMessage property.
     *
     * @param primaryKey The primary key to load from the database.
     * @return True = success; False = failure, so UI layer must call
     * getErrorMessage and display.
     */
    public abstract boolean load(Integer primaryKey);

    /**
     * Saves the object to the database from the BAL layer. Must catch
     * SQLException and handle it by setting errorMessage field.
     *
     * @return True = success; False = failure, so UI layer must call
     * getErrorMessage and display.
     */
    public abstract boolean save();

    /**
     * Deletes the object from the database. Must catch SQLException and handle
     * it by setting errorMessage property.
     *
     * @return True = success; False = failure, so UI layer must call
     * getErrorMessage and display.
     */
    public abstract boolean remove();
}
