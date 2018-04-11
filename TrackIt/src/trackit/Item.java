package trackit;

import java.sql.*;
import trackit.DAL.*;

/**
 * BAL Layer: Handles all aspects of a single Item.
 */
public abstract class Item
        extends DatabaseObject
        implements IDataAwareObject {

    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private String description;
    private String sku;
    private Float size;
    private String sizeUnit;
    private ItemStatusType itemStatus;
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">

    public Item() {
    }
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Setters & Getters">
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Public Methods">

    public Item(String description, String sku, Float size, String sizeUnit) {
        this();
        this.description = description;
        this.sku = sku;
        this.size = size;
        this.sizeUnit = sizeUnit;
    }

    @Override
    public boolean load() {
        return load(this.primaryKey);
    }

    @Override
    public boolean load(Integer primaryKey) {
        return false;
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean remove() {
        return false;
    }

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
    // </editor-fold>
}
