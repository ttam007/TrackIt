package trackit;

import java.util.*;

/**
 * BLL Layer: For all classes that deal with grids in the UI.
 *
 * @param <T extends DatabaseObject> The object that is in each row of the grid.
 * @author Bond
 */
public abstract class GridClass<T extends DatabaseObject> {

    /**
     * A list of all T objects in memory. Used to populate grids and what would
     * be loaded from/saved to the database.
     */
    protected ArrayList<T> rows = new ArrayList<>();

    /**
     * Gets the list of all T objects that are currently in memory.
     *
     * @return
     */
    public ArrayList<T> getList() {
        return this.rows;
    }

    /**
     * Loads all rows from the database to the grid and updates the list of T
     * objects in memory.
     *
     * @return True = The rows were successfully loaded; False = There was an
     * error.
     */
    protected abstract boolean load();

    /**
     * Loads an single object from the database into rows.
     *
     * @param primaryKey The primary key of the object to be loaded.
     * @return True = The object was successfully retrieved; False = There was
     * an error.
     */
    protected abstract boolean load(Integer primaryKey);

    /**
     * Saves all rows to the database from the grid and updates the list of T
     * objects in memory.
     *
     * @return True = The rows were successfully saved; False = There was an
     * error.
     */
    protected abstract boolean save();

    /**
     * Saves specified object to the database.
     *
     * @param anObj The object to be saved.
     * @return True = The object was successfully saved; False = There was an
     * error.
     */
    protected abstract boolean save(T anObj);

    /**
     * Tests the specified object to see if it will have any issues being
     * deleted from the database.
     *
     * @param anObj The object to test if it can be safely deleted from the
     * database.
     * @return True = An error will be thrown if attempting to delete the
     * specified object from the database; False = No issues should occur if
     * deleting the specified object from the database.
     */
    protected boolean hasForeignKeyIssue(T anObj) {
        return false;
    }

    /**
     * Removes a row from the database and updates the list of T objects in
     * memory.
     *
     * @param primaryKey The primary key of the row to be removed.
     * @return True = The row was successfully removed; False = There was an
     * error.
     */
    public boolean remove(Integer primaryKey) {
        boolean returnValue = false;
        if (this.load(primaryKey)) {
            T anObj = this.getList().get(0);
            returnValue = remove(anObj);
        }
        return returnValue;
    }

    /**
     * Removes a row from the database.
     *
     * @param anObj The object in the row to remove.
     * @return True = The row was successfully removed; False = There was an
     * error.
     */
    protected abstract boolean remove(T anObj);
}
