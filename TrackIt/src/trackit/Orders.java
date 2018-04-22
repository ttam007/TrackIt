package trackit;

import java.sql.*;
import java.util.ArrayList;
import trackit.DAL.SQLHelperOrder;

/**
 * BLL Layer: Works with the Orders Tab.
 *
 * @author
 */
public class Orders
        extends GridClass<AnOrder> {

    /**
     * Pulls SQL info from database to load into JTable
     *
     * @return SQLHelperOrder
     */
    public ArrayList<AnOrder> getSQL() {
        try {
            System.out.println("\nSelectAll");
            SQLHelperOrder helper = new SQLHelperOrder();
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
            rows = AnOrder.loadAll();
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
            for (AnOrder anItem : rows) {
                AnOrder.save(anItem);
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
    public boolean save(AnOrder anObj) {
        boolean returnValue = false;
        try {
            AnOrder.save(anObj);
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
            AnOrder.remove(primaryKey);
            returnValue = true;
        } catch (SQLException exSQL) {
            this.errorMessage = exSQL.getLocalizedMessage();
        } catch (Exception ex) {
            this.errorMessage = ex.getLocalizedMessage();
        }
        return returnValue;
    }
}
