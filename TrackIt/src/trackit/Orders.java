package trackit;

import java.sql.*;

/**
 * BLL Layer: Works with the Orders Tab.
 *
 * @author
 */
public class Orders
        extends GridClass<AnOrder> {

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
            rows = AnOrder.loadAll();
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
            rows.add(AnOrder.load(primaryKey));
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
            for (AnOrder anItem : rows) {
                AnOrder.save(anItem);
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
    public boolean save(AnOrder anObj) {
        boolean returnValue = false;
        try {
            AnOrder.save(anObj);
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
    public boolean remove(AnOrder anObj) {
        boolean returnValue = false;
        try {
            if (!this.hasForeignKeyIssue(anObj)) {
                AnOrder.remove(anObj.getPrimaryKey());
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
