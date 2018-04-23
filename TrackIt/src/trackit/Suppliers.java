package trackit;

import java.sql.*;

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
            ASupplier.remove(primaryKey);
            returnValue = true;
        } catch (SQLException exSQL) {
            Utilities.setErrorMessage(exSQL);
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
        }
        return returnValue;
    }
}
