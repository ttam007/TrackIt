package trackit;

import java.sql.*;
import java.util.*;
import trackit.DAL.SQLHelper;
import trackit.DAL.SQLHelperInventoryItem;
import trackit.DAL.SQLHelperOrderItem;

/**
 * DAL Layer: Handles all aspects of a single OrderItem.
 *
 * @author Bond
 */
public class AnOrderItem
        extends AnItem {

    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private static final SQLHelperOrderItem HELPER = new SQLHelperOrderItem();
    private Integer orderId = SQLHelper.INVALID_PRIMARY_KEY;
    private Integer itemId = SQLHelper.INVALID_PRIMARY_KEY;
    private Integer quantityOrdered = 1;
    private Integer quantityCheckedIn = 0;
    private Double price = 0d;

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">
    /**
     * Default constructor.
     */
    public AnOrderItem() {
        super();
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Setters & Getters">
    @Override
    public void setPrimaryKey(Integer aPrimaryKey)
            throws SQLException {
        this.primaryKey = HELPER.doNullCheck(SQLHelperOrderItem.COLUMN_PK, aPrimaryKey);
    }

    /**
     * This can not be null.
     *
     * @param orderId
     * @throws SQLException
     */
    public void setOrderId(Integer orderId)
            throws SQLException {
        this.orderId = HELPER.doNullCheck(SQLHelperOrderItem.COLUMN_ORDERID, orderId);
    }

    /**
     * This can not be null.
     *
     * @return
     */
    public Integer getOrderId() {
        return this.orderId;
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
     * @param quantityOrdered
     * @throws SQLException
     */
    public void setQuantityOrdered(Integer quantityOrdered)
            throws SQLException {
        this.quantityOrdered = HELPER.doNullCheck(SQLHelperOrderItem.COLUMN_QUANTITYORDERED, quantityOrdered);
    }

    /**
     * This can not be null.
     *
     * @return
     */
    public Integer getQuantityOrdered() {
        return this.quantityOrdered;
    }

    /**
     * This can not be null.
     *
     * @param quantityCheckedIn
     * @throws SQLException
     */
    public void setQuantityCheckedIn(Integer quantityCheckedIn)
            throws SQLException {
        this.quantityCheckedIn = HELPER.doNullCheck(SQLHelperOrderItem.COLUMN_QUANTITYCHECKEDIN, quantityCheckedIn);
    }

    /**
     * This can not be null.
     *
     * @return
     */
    public Integer getQuantityCheckedIn() {
        return this.quantityCheckedIn;
    }

    /**
     * This can not be null.
     *
     * @param price
     * @throws SQLException
     */
    public void setPrice(Double price)
            throws SQLException {
        this.price = HELPER.doNullCheck(SQLHelperOrderItem.COLUMN_PRICE, price);
    }

    /**
     * This can not be null.
     *
     * @return
     */
    public Double getPrice() {
        return this.price;
    }

    /**
     * This can not be null.
     *
     * @return
     */
    public Double getExtendedPrice() {
        return this.calcExtendedPrice();
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private methods">
    /**
     * Calculates the extended price. Should be used any time the
     * quantityOrdered or the price changes.
     */
    private Double calcExtendedPrice() {
        return this.quantityOrdered * this.price;
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
    public static ArrayList<AnOrderItem> loadAll()
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
    public static AnOrderItem load(int primaryKey)
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
    public static void save(AnOrderItem anObj)
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
    public static void remove(AnOrderItem anObj)
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

    @Override
    public void changeQuantity(int amountToChangeBy)
            throws NegativeAmountException {
        if (this.quantityOrdered + amountToChangeBy < 0) {
            throw new NegativeAmountException();
        }
        this.quantityOrdered += amountToChangeBy;
        calcExtendedPrice();
    }
    // </editor-fold>
}
