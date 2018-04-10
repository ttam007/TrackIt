package trackit;

import java.sql.*;
import java.util.*;
import trackit.DAL.*;

/**
 * BAL Layer: Handles all aspects of a single Item in Inventory.
 */
public class InventoryItem
        extends Item
        implements IDataAwareObject {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private Integer quantity;
    private java.util.Date expirationDate;
    // </editor-fold>

    public InventoryItem() {

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
    public boolean load(Integer primaryKey) {
        //TODO:  load all fields from database.
        //TODO:  catch IllegalArgumentException, SQLException.
        //TODO:  If nothing to load from database, then set fields with default values.
        return false;
    }

    @Override
    public boolean save() {
        boolean returnValue = false;
        /*
        try {
            if (this.isAlreadyInDatabase()){
                //TODO:  call Update sproc.
            } else {
                //TODO:  call Insert sproc.
                //TODO:  set primary key from returned value.
            }
            returnValue = true;
        } catch (SQLException exSQL) {
            this.errorMessage = exSQL.getLocalizedMessage();
        }
         */
        return returnValue;
    }

    @Override
    public boolean remove() {
        //TODO:  remove from database.  Catch SQLException.
        return false;
    }

    public ArrayList<Item> getExpiredItems() {
        ArrayList<Item> returnValue = new ArrayList<>();
        return returnValue;
    }

    @Override
    public void changeQuantity(int amountToChangeBy)
            throws NegativeAmountException {
        throw new NegativeAmountException();
    }
}
