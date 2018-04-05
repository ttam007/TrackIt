package trackit;

import java.sql.*;
import trackit.DAL.*;

/**
 * BAL Layer: Handles all aspects of a single Item.
 */
public abstract class Item
        extends DatabaseObject
        implements IDataAwareObject {

    private String description;
    private String sku;
    private Float size;
    private String sizeUnit;
    private ItemStatusType itemStatus;

    public Item() {
    }

    public Item(String description, String sku, Float size, String sizeUnit) {
        this();
        this.description = description;
        this.sku = sku;
        this.size = size;
        this.sizeUnit = sizeUnit;
    }

    @Override
    public Integer getPrimaryKey() {
        return this.primaryKey;
    }

    @Override
    public boolean load() {
        return load(this.primaryKey);
    }

    @Override
    public abstract boolean load(Integer primaryKey);

    @Override
    public abstract boolean save();

    @Override
    public abstract boolean remove();

    /**
     * Changes the quantity of this item by the specified amount.
     *
     * @param amountToChangeBy The amount the quantity should change by. A
     * negative number reduces the quantity while a positive number increases
     * it.
     * @throws NegativeAmountException If the @amountToChangeBy reduces the
     * quantity below zero, then this error will be thrown.
     */
    abstract void changeQuantity(int amountToChangeBy);
}
