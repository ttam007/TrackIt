package trackit.DAL;

import java.sql.*;
import java.util.*;
import trackit.*;

/**
 * DAL Layer: Handles all aspects of a single OrderItem.
 *
 * @author Bond
 */
public class AnOrderItem
        extends AnItem {

    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private static final SQLHelperOrderItem HELPER = new SQLHelperOrderItem();
    private Integer orderId;
    private Integer quantityOrdered = 1;
    private Integer quantityCheckedIn = 0;
    private Double price = 0d;
    private Double extendedPrice = 0d;

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">
    /**
     * order item
     */
    public AnOrderItem() {
        super();
    }
    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Setters & Getters">
    /**
     * Calculates the extended price. Should be used any time the
     * quantityOrdered or the price changes.
     */
    private void calcExtendedPrice() {
        this.extendedPrice = this.quantityOrdered * this.price;
    }
    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Setters & Getters">
    /**
     *
     * @param orderId
     * @throws SQLException
     */
    public void setOrderId(Integer orderId)
            throws SQLException {
        this.orderId = HELPER.doNullCheck(SQLHelperOrderItem.COLUMN_ORDERID, orderId);
    }

    /**
     *
     * @return
     */
    public Integer getOrderId() {
        return this.orderId;
    }

    /**
     *
     * @param quantityOrdered
     * @throws SQLException
     */
    public void setQuantityOrdered(Integer quantityOrdered)
            throws SQLException {
        this.quantityOrdered = HELPER.doNullCheck(SQLHelperOrderItem.COLUMN_QUANTITYORDERED, quantityOrdered);
        calcExtendedPrice();
    }

    /**
     *
     * @return
     */
    public Integer getQuantityOrdered() {
        return this.quantityOrdered;
    }

    /**
     *
     * @param quantityCheckedIn
     * @throws SQLException
     */
    public void setQuantityCheckedIn(Integer quantityCheckedIn)
            throws SQLException {
        this.quantityCheckedIn = HELPER.doNullCheck(SQLHelperOrderItem.COLUMN_QUANTITYCHECKEDIN, quantityCheckedIn);
    }

    /**
     *
     * @return
     */
    public Integer getQuantityCheckedIn() {
        return this.quantityCheckedIn;
    }

    /**
     *
     * @param price
     * @throws SQLException
     */
    public void setPrice(Double price)
            throws SQLException {
        this.price = HELPER.doNullCheck(SQLHelperOrderItem.COLUMN_PRICE, price);
        calcExtendedPrice();
    }

    /**
     *
     * @return
     */
    public Double getPrice() {
        return this.price;
    }

    /*
    public void setExtendedPrice(Double extendedPrice)
            throws SQLException {
        this.extendedPrice = HELPER.doNullCheck(HELPER.COLUMN_EXTENDEDPRICE, extendedPrice);
    }*/
    /**
     *
     * @return
     */
    public Double getExtendedPrice() {
        return this.extendedPrice;
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

    @Override
    public void changeQuantity(int amountToChangeBy)
            throws NegativeAmountException {
        if (this.quantityOrdered + amountToChangeBy < 0) {
            throw new NegativeAmountException();
        }
        this.quantityOrdered += amountToChangeBy;
        calcExtendedPrice();
    }
}
