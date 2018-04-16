package trackit.DAL;

import java.sql.*;
import java.util.*;
import trackit.*;

/**
 * DAL Layer: Handles all aspects of a single InventoryItem.
 *
 * @author Bond
 */
public class AnInventoryItem
        extends AnItem {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private static final SQLHelperInventoryItem HELPER = new SQLHelperInventoryItem();
    private Integer quantity = 0;
    private java.sql.Date expirationDate;
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">

    /**
     * add an inventory item
     */
    public AnInventoryItem() {
        super();
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Setters & Getters">
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Public Static Methods">
    /**
     * Gets all the objects from the database.
     *
     * @return A list of objects with values loaded from the database.
     * @throws SQLException
     * @throws Exception
     */
    public static ArrayList<AnInventoryItem> loadAll()
            throws SQLException, Exception {
        return HELPER.selectAll();
    }

    /**
     * Gets the object from the database.
     *
     * @param primaryKey The primary key in the table to retrieve.
     * @return The object with values loaded from the database, or a null object
     * if not found.
     * @throws SQLException
     * @throws Exception
     */
    public static AnInventoryItem load(int primaryKey)
            throws SQLException, Exception {
        return HELPER.selectOne(primaryKey);
    }

    /**
     * Saves the specified object to the database.
     *
     * @param anObj The object to be saved.
     * @throws SQLException
     * @throws Exception
     */
    public static void save(AnInventoryItem anObj)
            throws SQLException, Exception {
        if (anObj.isAlreadyInDatabase()) {
            HELPER.update(anObj);
        } else {
            HELPER.insert(anObj);
        }
    }

    /**
     * Removes the specific object from the database.
     *
     * @param anObj The object to be saved.
     * @throws SQLException
     * @throws Exception
     */
    public static void remove(AnInventoryItem anObj)
            throws SQLException, Exception {
        remove(anObj.getPrimaryKey());
    }

    /**
     * Removes the specific primaryKey from the database.
     *
     * @param primaryKey The primary key to be deleted.
     * @throws SQLException
     * @throws Exception
     */
    public static void remove(Integer primaryKey)
            throws SQLException, Exception {
        HELPER.delete(primaryKey);
    }
    // </editor-fold>

    /**
     *
     * @return
     */
    public ArrayList<AnItem> getExpiredItems() {
        ArrayList<AnItem> returnValue = new ArrayList<>();
        return returnValue;
    }

    @Override
    public void changeQuantity(int amountToChangeBy)
            throws NegativeAmountException {
        if (this.quantity + amountToChangeBy < 0) {
            throw new NegativeAmountException();
        }
        this.quantity += amountToChangeBy;
    }
    // </editor-fold>
}
