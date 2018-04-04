package trackit;

import java.sql.*;
import java.util.*;
import trackit.DAL.*;

/**
 * BAL Layer: Handles all aspects of a single Order.
 */
public class Order
        extends DatabaseObject
        implements IDataAwareObject {

    private String description;
    private Supplier orderedFrom;
    private java.util.Date dateOrdered;
    private OrderStatusType orderStatus;
    private java.util.Date dateExpected;

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
