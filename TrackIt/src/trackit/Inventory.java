package trackit;

import java.sql.*;
import java.util.*;
import trackit.DAL.*;

/**
 * BAL Layer: Handles all aspects of the Inventory.
 */
public class Inventory
        extends DatabaseObject
        implements IDataAwareObject {

    private final ArrayList<InventoryItem> items = new ArrayList<>();

    @Override
    public Integer getPrimaryKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean load() {
        //TODO:  load all InventoryItems from database into this.items.
        //TODO:  catch SQLException.
        return false;
    }

    @Override
    public boolean load(Integer primaryKey) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    public ArrayList<InventoryItem> getItems() {
        return this.items;
    }
}
