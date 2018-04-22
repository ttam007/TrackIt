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
            for (AnOrderItem anItem : rows) {
                AnOrderItem.save(anItem);
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
    public boolean save(AnOrderItem anObj) {
        boolean returnValue = false;
        try {
            AnOrderItem.save(anObj);
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
            AnOrderItem.remove(primaryKey);
            returnValue = true;
        } catch (SQLException exSQL) {
            this.errorMessage = exSQL.getLocalizedMessage();
        } catch (Exception ex) {
            this.errorMessage = ex.getLocalizedMessage();
        }
        return returnValue;
    }
}
