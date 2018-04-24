package trackit;

import java.sql.*;
import java.util.*;

/**
 * BLL Layer: Works with the Suppliers Tab.
 *
 * @author
 */
public class Suppliers
        extends GridClass<ASupplier> {

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
            rows = ASupplier.loadAll();
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
            rows.add(ASupplier.load(primaryKey));
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
            for (ASupplier anItem : rows) {
                ASupplier.save(anItem);
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
    public boolean save(ASupplier anObj) {
        boolean returnValue = false;
        try {
            ASupplier.save(anObj);
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
    protected boolean hasForeignKeyIssue(ASupplier anObj) {
        boolean returnValue = false;

        ArrayList<AnOrder> orders = new ArrayList<>();
        Orders bllOrders = new Orders();
        if (bllOrders.load()) {
            orders = bllOrders.getList();
        }

        for (AnOrder anOrder : orders) {
            if (anOrder.getOrderedFrom().equals(anObj.getPrimaryKey())) {
                Utilities.setErrorMessage(new ForeignKeyException(
                        anObj.getNickname(), "Order",
                        anOrder.getDescription(), false));
                returnValue = true;
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
    public boolean remove(ASupplier anObj) {
        boolean returnValue = false;
        try {
            if (!this.hasForeignKeyIssue(anObj)) {
                ASupplier.remove(anObj.getPrimaryKey());
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
