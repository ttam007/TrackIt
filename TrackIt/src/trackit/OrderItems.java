package trackit;

import java.sql.*;

/**
 * BLL Layer: Works with the OrderItems frame.
 *
 * @author Bond
 */
public class OrderItems
        extends GridClass<AnOrderItem> {

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
            rows = AnOrderItem.loadAll();
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
            rows.add(AnOrderItem.load(primaryKey));
            returnValue = true;
        } catch (SQLException exSQL) {
            Utilities.setErrorMessage(exSQL);
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
        }
        return returnValue;
    }

    /**
     * Loads all order items for the specified order.
     *
     * @param primaryKey The primary key of the order.
     * @return True = The objects were successfully retrieved; False = There was
     * an error.
     */
    public boolean loadByOrder(Integer primaryKey) {
        boolean returnValue = false;
        try {
            rows = AnOrderItem.loadByOrder(primaryKey);
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
            for (AnOrderItem anItem : rows) {
                AnOrderItem.save(anItem);
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
    public boolean save(AnOrderItem anObj) {
        boolean returnValue = false;
        try {
            AnOrderItem.save(anObj);
            returnValue = true;
        } catch (java.sql.SQLException exSQL) {
            Utilities.setErrorMessage(exSQL);
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
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
    public boolean remove(AnOrderItem anObj) {
        boolean returnValue = false;
        try {
            if (!this.hasForeignKeyIssue(anObj)) {
                AnOrderItem.remove(anObj);
                returnValue = true;
            }
        } catch (SQLException exSQL) {
            Utilities.setErrorMessage(exSQL);
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
        }
        return returnValue;
    }

    /**
     * Gets the sum of the extended price field for all items in the current
     * list.
     *
     * @return The sum of the extended price.
     */
    public Double getSumOfExtendedPrice() {
        Double sum = 0.00;
        for (AnOrderItem anItem : this.getList()) {
            sum += anItem.getExtendedPrice();
        }
        return sum;
    }
}
