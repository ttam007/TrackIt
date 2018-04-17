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
     * The last error message generated.
     */
    protected String errorMessage;

    /**
     * Loads all rows from the database to the grid and updates the list of T
     * objects in memory.
     *
     * @return True = The rows were successfully loaded; False = There was an
     * error.
     */
    protected abstract boolean load();

    /**
     * Saves all rows to the database from the grid and updates the list of T
     * objects in memory.
     *
     * @return True = The rows were successfully saved; False = There was an
     * error.
     */
    protected abstract boolean save();

    /**
     * Removes a row from the database and updates the list of T objects in
     * memory.
     *
     * @param primaryKey The primary key of the row to be removed.
     * @return True = The row was successfully removed; False = There was an
     * error.
     */
    protected abstract boolean remove(Integer primaryKey);

    /**
     * Gets the list of all T objects that are currently in memory.
     *
     * @return
     */
    public ArrayList<T> getList() {
        return this.rows;
    }

    /**
     * Gets the last error message generated.
     *
     * @return
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
