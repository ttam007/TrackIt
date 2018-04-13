package trackit.DAL;

import java.sql.*;
import trackit.*;

/**
 * DAL Layer: Handles all aspects of a single Item.
 *
 * @author Bond
 */
public abstract class AnItem
        extends DatabaseObject {

    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private static final SQLHelperItem HELPER = new SQLHelperItem();
    protected String description;
    protected String sku;
    protected String sizeUnit;
    protected ItemStatusType itemStatus;
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">

    public AnItem() {
        this.primaryKey = SQLHelper.INVALID_PRIMARY_KEY;
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Setters & Getters">
    public void setDescription(String description)
            throws SQLException {
        this.description = HELPER.doNullCheck(HELPER.COLUMN_DESCRIPTION, description);
    }

    public String getDescription() {
        return this.description;
    }

    public void setSku(String sku)
            throws SQLException {
        this.sku = HELPER.doNullCheck(HELPER.COLUMN_SKU, sku);
    }

    public String getSku() {
        return this.sku;
    }

    public void setSizeUnit(String sizeUnit)
            throws SQLException {
        this.sizeUnit = HELPER.doNullCheck(HELPER.COLUMN_SIZEUNIT, sizeUnit);
    }

    public String getSizeUnit() {
        return this.sizeUnit;
    }

    public void setItemStatus(String itemStatus)
            throws SQLException {
        String tmpValue = HELPER.doNullCheck(HELPER.COLUMN_ITEMSTATUS, itemStatus);
        this.itemStatus = ItemStatusType.getType(tmpValue);
    }

    public void setOrderStatus(ItemStatusType itemStatus)
            throws SQLException {
        //Calls the overloaded method instead of directly setting so the null check can occur.
        setItemStatus(itemStatus.getText());
    }

    public ItemStatusType getItemStatus() {
        return this.itemStatus;
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Public Methods">
    /**
     * Changes the quantity of this item by the specified amount.
     *
     * @param amountToChangeBy The amount the quantity should change by. A
     * negative number reduces the quantity while a positive number increases
     * it.
     * @throws NegativeAmountException If the @amountToChangeBy reduces the
     * quantity below zero, then this error will be thrown.
     */
    protected abstract void changeQuantity(int amountToChangeBy)
            throws NegativeAmountException;
    // </editor-fold>
}
