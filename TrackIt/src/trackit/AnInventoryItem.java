package trackit;

import java.sql.*;
import java.util.*;
import trackit.DAL.SQLHelper;
import trackit.DAL.SQLHelperInventoryItem;

/**
 * DAL Layer: Handles all aspects of a single InventoryItem.
 *
 * @author Bond
 */
public class AnInventoryItem
        extends AnItem {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private static final SQLHelperInventoryItem HELPER = new SQLHelperInventoryItem();
    private Integer itemId = SQLHelper.INVALID_PRIMARY_KEY;
    private Integer quantity = 0;
    private java.sql.Date expirationDate;

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">
    /**
     * Default constructor.
     */
    public AnInventoryItem() {
        super();
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Setters & Getters">
    @Override
    public void setPrimaryKey(Integer aPrimaryKey)
            throws SQLException {
        this.primaryKey = HELPER.doNullCheck(SQLHelperInventoryItem.COLUMN_PK, aPrimaryKey);
    }

    /**
     * This can not be null.
     *
     * @param anItemId
     * @throws SQLException
     */
    public void setItemId(Integer anItemId)
            throws SQLException {
        this.itemId = HELPER.doNullCheck(SQLHelperInventoryItem.COLUMN_ITEMID, anItemId);
    }

    /**
     * This can not be null.
     *
     * @return
     */
    public Integer getItemId() {
        return this.itemId;
    }

    /**
     * This can not be null.
     *
     * @param aQuantity
     * @throws SQLException
     */
    public void setQuantity(Integer aQuantity)
            throws SQLException {
        this.quantity = HELPER.doNullCheck(SQLHelperInventoryItem.COLUMN_ITEMID, aQuantity);
    }

    /**
     * This can not be null.
     *
     * @return
     */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     * This can be null.
     *
     * @param anExpirationDate
     * @throws SQLException
     */
    public void setExpirationDate(java.sql.Date anExpirationDate)
            throws SQLException {
        this.expirationDate = HELPER.doNullCheck(SQLHelperInventoryItem.COLUMN_EXPIRATIONDATE, anExpirationDate);
    }

    /**
     * This can be null.
     *
     * @return
     */
    public java.sql.Date getExpirationDate() {
        if (this.expirationDate == null) {
            return null;
        } else {
            return (java.sql.Date) this.expirationDate.clone();
        }
    }
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
    // <editor-fold defaultstate="expanded" desc="Public Methods">

    /**
     * Gets a list of all expired items.
     *
     * @return
     */
    public ArrayList<AnInventoryItem> getExpiredItems() {
        ArrayList<AnInventoryItem> returnList = new ArrayList<>();

        try {
            java.util.Date aUtilDate = Calendar.getInstance().getTime();
            java.sql.Date aSQLDate = Utilities.convertToSQLDate(aUtilDate);
            ArrayList<AnInventoryItem> aList = loadAll();
            for (AnInventoryItem anItem : aList) {
                if (anItem.getExpirationDate().before(aSQLDate)) {
                    returnList.add(anItem);
                }
            }
        } catch (SQLException exSQL) {
            //TODO: handle this
        } catch (Exception ex) {
            //TODO: handle this
        }

        return returnList;
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
