package trackit;

import java.sql.*;
import java.util.ArrayList;

/**
 * BLL Layer: Works with the Inventory tab.
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
            Utilities.setErrorMessage(exSQL);
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
        }
        return returnValue;
    }

    /**
     * Loads an single object from the database into rows.
     *
     * @param primaryKey The primary key of the object to be loaded.
     * @return True = The object was successfully retrieved; False = There was
     * an error.
     */
    @Override
    public boolean load(Integer primaryKey) {
        boolean returnValue = false;
        try {
            rows.clear();
            rows.add(AnInventoryItem.load(primaryKey));
            returnValue = true;
        } catch (SQLException exSQL) {
            Utilities.setErrorMessage(exSQL);
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
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
            Utilities.setErrorMessage(exSQL);
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
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
            Utilities.setErrorMessage(exSQL);
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
        }
        return returnValue;
    }

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
    @Override
    protected boolean hasForeignKeyIssue(AnInventoryItem anObj) {
        boolean returnValue = false;

        ArrayList<AnOrderItem> orderItems = new ArrayList<>();
        OrderItems bllOrderItems = new OrderItems();
        if (bllOrderItems.load()) {
            orderItems = bllOrderItems.getList();
        }

        for (AnOrderItem anOrderItem : orderItems) {
            if (anOrderItem.getItemId().equals(anObj.getPrimaryKey())) {
                Utilities.setErrorMessage(new ForeignKeyException(
                        anObj.getDescription(), "Order Item",
                        anOrderItem.getDescription(), true));
                returnValue = true;
                break;
            }
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
    @Override
    public boolean remove(AnInventoryItem anObj) {
        boolean returnValue = false;
        try {
            if (!this.hasForeignKeyIssue(anObj)) {
                AnInventoryItem.remove(anObj);
                returnValue = true;
            }
        } catch (SQLException exSQL) {
            Utilities.setErrorMessage(exSQL);
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
        }
        return returnValue;
    }
}
