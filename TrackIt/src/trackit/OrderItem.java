package trackit;

import java.sql.*;
import java.util.*;
import trackit.DAL.*;

/**
 * BAL Layer: Handles all aspects of a single OrderItem.
 */
public class OrderItem
        extends Item
        implements IDataAwareObject {

    private Order order;
    private Integer quantityOrdered;
    private Float price;
    private Float extendedPrice;

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
    public void changeQuantity(int amountToChangeBy)
            throws NegativeAmountException {
        throw new NegativeAmountException();
    }
}
