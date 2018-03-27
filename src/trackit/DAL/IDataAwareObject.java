package trackit.DAL;

import java.sql.*;

/**
 * Methods for an object that are necessary for interacting with the database.
 */
public interface IDataAwareObject {

    /**
     * Gets the primaryKey property for this object.
     *
     * @return The primary key in the database for this object.
     */
    public Integer getPrimaryKey();

    /**
     * Sets the primaryKey property for this object. Can't be changed once set.
     *
     * @param primaryKey The primary key in the database for this object.
     * @throws IllegalArgumentException If the primaryKey property already has a
     * value.
     */
    public void setPrimaryKey(Integer primaryKey)
            throws IllegalArgumentException;

    /**
     * Loads this object from the database. The primary key for this object must
     * be set first.
     *
     * @throws IllegalArgumentException If the primary key is not set first.
     * @throws SQLException If any issue arises from the database calls.
     */
    public void load()
            throws IllegalArgumentException, SQLException;

    /**
     * Loads this object from the database. Sets the primary key first.
     *
     * @param primaryKey The primary key of the object to be loaded from the
     * database.
     * @throws IllegalArgumentException If the primary key is already set.
     * @throws SQLException If any issue arises from the database calls.
     */
    public void load(Integer primaryKey)
            throws IllegalArgumentException, SQLException;

    /**
     * Saves this object to the database.
     *
     * @throws SQLException If any issue arises from the database calls.
     */
    public void save()
            throws SQLException;

    /**
     * Deletes this object from the database.
     *
     * @throws SQLException If any issue arises from the database calls.
     */
    public void remove()
            throws SQLException;
}
