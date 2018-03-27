package trackit;

import trackit.DAL.IDataAwareObject;
import java.sql.*;
import java.util.*;

/**
 * BAL Layer that handles the inventory features.
 */
public class Inventory
        implements IDataAwareObject, IItemHandler {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    /**
     * Can not be final as the primary key is not set until a save is done.
     */
    private Integer primaryKey = null;

    private final ArrayList<Item> items = new ArrayList<>();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public Inventory() {

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public Integer getPrimaryKey() {
        return this.primaryKey;
    }

    @Override
    public void setPrimaryKey(Integer primaryKey)
            throws IllegalArgumentException {
        if (this.primaryKey == null) {
            throw new IllegalArgumentException();
        }
        this.primaryKey = primaryKey;
    }

    @Override
    public void load()
            throws IllegalArgumentException, SQLException {

    }

    @Override
    public void load(Integer primaryKey)
            //Set primary key property first, then call load().
            throws IllegalArgumentException, SQLException {

    }

    @Override
    public void save()
            throws SQLException {
        //Saves to database, then sets primaryKey value.
    }

    @Override
    public void remove()
            throws SQLException {

    }

    @Override
    public void addItem(Item anItem) {

    }

    @Override
    public void removeItem(Item anItem) {

    }

    @Override
    public void reduceItem(Item anItem, Integer quantity)
            throws NegativeAmountException {

    }

    public ArrayList<Item> getExpiredItems() {
        ArrayList<Item> returnValue = new ArrayList<>();

        return returnValue;
    }
    // </editor-fold>
}
