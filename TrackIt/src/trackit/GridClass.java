package trackit;

import java.util.*;
import trackit.DAL.*;

/**
 * BLL Layer: For all classes that deal with grids in the UI.
 *
 * @param <T extends DatabaseObject> The object that is in each row of the grid.
 * @author Bond
 */
public abstract class GridClass<T extends DatabaseObject> {

    protected ArrayList<T> rows = new ArrayList<>();
    protected String errorMessage;

    /**
     * Loads all rows from the database to the grid.
     *
     * @return True = The rows were successfully loaded; False = There was an
     * error.
     */
    protected abstract boolean load();

    /**
     * Saves all rows to the database from the grid.
     *
     * @return True = The rows were successfully saved; False = There was an
     * error.
     */
    protected abstract boolean save();

    /**
     * Removes a row from the database.
     *
     * @param primaryKey The primary key of the row to be removed.
     * @return True = The row was successfully removed; False = There was an
     * error.
     */
    protected abstract boolean remove(Integer primaryKey);

    public ArrayList<T> getList() {
        return this.rows;
    }
}
