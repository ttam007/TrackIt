package trackit;

import java.sql.*;
import java.util.*;
import trackit.DAL.*;

/**
 * BAL Layer:  Handles all aspects of a single Item in Inventory.
 */
public class InventoryItem
        extends Item
        implements IDataAwareObject,
        IItemHandler {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private Integer quantity;
    private java.util.Date expirationDate;
    // </editor-fold>

    public InventoryItem(){
        
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
        return false;
    }

    @Override
    public boolean save() {
        boolean returnValue = false;
        /*
        try {
            if (this.isAlreadyInDatabase()){
                //TODO:  call Update sproc
            } else {
                //TODO:  call Insert sproc
            }
            returnValue = true;
        } catch (SQLException exSQL) {
            //TODO:  set this.errorMessage.
        }
         */
        return returnValue;
    }

    @Override
    public boolean remove() {
        //TODO:  remove from database.  Catch SQLException.
        return false;
    }

    @Override
    public void addItem(Item anItem) {
    }

    @Override
    public void removeItem(Item anItem) {
    }

    @Override
    public void reduceItem(Item anItem, Integer quantity) throws NegativeAmountException {
    }

    public ArrayList<Item> getExpiredItems() {
        ArrayList<Item> returnValue = new ArrayList<>();
        return returnValue;
    }
}
