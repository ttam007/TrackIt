package trackit;

import java.sql.*;
import java.util.*;
import trackit.DAL.*;

/**
 * BAL Layer: Handles all aspects of a single Supplier.
 */
public class Supplier
        extends DatabaseObject
        implements IDataAwareObject {

    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final SQLHelperSupplier helper = new SQLHelperSupplier();
    private String nickname = null;
    private String url = null;
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">

    public Supplier() {
        this.primaryKey = SQLHelperSupplier.INVALID_PRIMARY_KEY;

    }

    public Supplier(String nickname, String url)
            throws SQLException {
        this();
        this.nickname = helper.doNullCheck(helper.COLUMN_NICKNAME, nickname);
        this.url = helper.doNullCheck(helper.COLUMN_URL, url);
    }

    public Supplier(Integer primaryKey, String nickname, String url)
            throws SQLException {
        this(nickname, url);
        this.primaryKey = helper.doNullCheck(helper.COLUMN_PK, primaryKey);
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Setters & Getters">
    public void setNickname(String nickname)
            throws SQLException {
        this.nickname = helper.doNullCheck(helper.COLUMN_NICKNAME, nickname);
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setUrl(String url)
            throws SQLException {
        this.url = helper.doNullCheck(helper.COLUMN_URL, url);
    }

    public String getUrl() {
        return this.url;
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Public Methods">
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
    // </editor-fold>
}
