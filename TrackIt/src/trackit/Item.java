package trackit;

import java.sql.*;
import trackit.DAL.*;

/**
 * BAL Layer:  Handles all aspects of a single Item.
 */
public class Item
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
}
