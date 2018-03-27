package trackit;

import trackit.DAL.IDataAwareObject;
import java.sql.*;

/**
 * BAL Layer that handles all things related to a single Item.
 */
public class Item implements IDataAwareObject {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    /**
     * Can not be final as the primary key is not set until a save is done.
     */
    private Integer primaryKey = null;

    private String description;
    private String sku;
    private Float size;
    private String sizeUnit; //Should this be an enum?

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public Item() {

    }

    public Item(String description, String sku, Float size, String sizeUnit) {
        this();

        this.description = description;
        this.sku = sku;
        this.size = size;
        this.sizeUnit = sizeUnit;
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

    // </editor-fold>  
}
