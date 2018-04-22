package trackit;

import java.sql.*;
import trackit.DAL.SQLHelper;
import trackit.DAL.SQLHelperItem;

/**
 * DAL Layer: Handles all aspects of a single Item.
 *
 * @author Bond
 */
public abstract class AnItem
        extends DatabaseObject {

    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private static final SQLHelperItem HELPER = new SQLHelperItem();

    /**
     *
     */
    protected String description = "New Item";

    /**
     *
     */
    protected String sku;

    /**
     *
     */
    protected String sizeUnit;

    /**
     *
     */
    protected ItemStatusType itemStatus = ItemStatusType.AVAILABLE;
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">

    /**
     * establish an item
     */
    public AnItem() {
        this.primaryKey = SQLHelper.INVALID_PRIMARY_KEY;
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Setters & Getters">
    @Override
    public void setPrimaryKey(Integer primaryKey)
            throws SQLException {
        throw new UnsupportedOperationException();
        //this.primaryKey = HELPER.doNullCheck(SQLHelperItem.COLUMN_PK, primaryKey);
    }

    /**
     * This can not be null.
     *
     * @param description
     * @throws SQLException
     */
    public void setDescription(String description)
            throws SQLException {
        this.description = HELPER.doNullCheck(SQLHelperItem.COLUMN_DESCRIPTION, description);
    }

    /**
     * This can not be null.
     *
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * This can be null.
     *
     * @param sku
     * @throws SQLException
     */
    public void setSku(String sku)
            throws SQLException {
        this.sku = HELPER.doNullCheck(SQLHelperItem.COLUMN_SKU, sku);
    }

    /**
     * This can be null.
     *
     * @return
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * This can be null.
     *
     * @param sizeUnit
     * @throws SQLException
     */
    public void setSizeUnit(String sizeUnit)
            throws SQLException {
        this.sizeUnit = HELPER.doNullCheck(SQLHelperItem.COLUMN_SIZEUNIT, sizeUnit);
    }

    /**
     * This can be null.
     *
     * @return
     */
    public String getSizeUnit() {
        return this.sizeUnit;
    }

    /**
     * This can not be null.
     *
     * @param itemStatus
     * @throws SQLException
     */
    public void setItemStatus(String itemStatus)
            throws SQLException {
        String tmpValue = HELPER.doNullCheck(SQLHelperItem.COLUMN_ITEMSTATUS, itemStatus);
        this.itemStatus = ItemStatusType.getType(tmpValue);
    }

    /**
     * This can not be null.
     *
     * @param itemStatus
     * @throws SQLException
     */
    public void setItemStatus(ItemStatusType itemStatus)
            throws SQLException {
        //Calls the overloaded method instead of directly setting so the null check can occur.
        setItemStatus(itemStatus.getText());
    }

    /**
     * This can not be null.
     *
     * @return
     */
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
