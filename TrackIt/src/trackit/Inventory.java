package trackit;

import java.sql.*;
import java.util.ArrayList;
import trackit.DAL.SQLHelperInventoryItem;

/**
 * BAL Layer: Works with the Inventory tab.
 *
 * @author Bond
 */
public class Inventory
        extends GridClass<AnInventoryItem> {

    /**
     * Pulls SQL info from database to load into JTable
     * @return SQLHelperInventoryItem
     */
    public ArrayList<AnInventoryItem> getSQL() {
        try {
            System.out.println("\nSelectAll");
            SQLHelperInventoryItem helper = new SQLHelperInventoryItem();
            rows = helper.selectAll();
        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }
        return rows;
    }

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
     * Saves an object to the database.
     *
     * @return True = The object was successfully saved; False = There was an
     * error.
     */
    @Override
    public boolean save(AnInventoryItem anObj) {
        boolean returnValue = false;
        try {
            AnInventoryItem.save(anObj);
            returnValue = true;
        } catch (java.sql.SQLException exSQL) {
            anObj.setErrorMessage(exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            anObj.setErrorMessage(ex.getLocalizedMessage());
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
