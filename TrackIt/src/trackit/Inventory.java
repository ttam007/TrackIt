package trackit;

import java.sql.*;

/**
 * BAL Layer: Works with the Inventory tab.
 *
 * @author Bond
 */
public class Inventory
        extends GridClass<AnInventoryItem> {

    /**
     * Loads all rows from the database to the grid.
     *
     * @return True = The rows were successfully loaded; False = There was an
     * error.
     */
    @Override
    public boolean load() {
        boolean returnValue = false;
        try {
            rows = AnInventoryItem.loadAll();
            returnValue = true;
        } catch (SQLException exSQL) {
            this.errorMessage = exSQL.getLocalizedMessage();
        } catch (Exception ex) {
            this.errorMessage = ex.getLocalizedMessage();
        }
        return returnValue;
    }

    /**
     * Saves all rows to the database from the grid.
     *
     * @return True = The rows were successfully saved; False = There was an
     * error.
     */
    @Override
    public boolean save() {
        boolean returnValue = false;
        try {
            for (AnInventoryItem anItem : rows) {
                AnInventoryItem.save(anItem);
            }
            returnValue = true;
        } catch (SQLException exSQL) {
            this.errorMessage = exSQL.getLocalizedMessage();
        } catch (Exception ex) {
            this.errorMessage = ex.getLocalizedMessage();
        }
        return returnValue;
    }

    /**
     * Removes a row from the database.
     *
     * @param primaryKey The primary key of the row to remove.
     * @return True = The row was successfully removed; False = There was an
     * error.
     */
    @Override
    public boolean remove(Integer primaryKey) {
        boolean returnValue = false;
        try {
            AnInventoryItem.remove(primaryKey);
            returnValue = true;
        } catch (SQLException exSQL) {
            this.errorMessage = exSQL.getLocalizedMessage();
        } catch (Exception ex) {
            this.errorMessage = ex.getLocalizedMessage();
        }
        return returnValue;
    }
}
