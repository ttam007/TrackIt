package trackit;

import java.sql.*;
import java.util.*;
import trackit.DAL.SQLHelper;
import trackit.DAL.SQLHelperOrder;

/**
 * DAL Layer: Handles all aspects of a single Order.
 *
 * @author Bond
 */
public class AnOrder
        extends DatabaseObject {

    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private static final SQLHelperOrder HELPER = new SQLHelperOrder();
    private String description = "New Order";
    /**
     * FK to suppliers.supplierId
     */
    private Integer orderedFrom = null;
    private OrderStatusType orderStatus = OrderStatusType.CREATED;
    private java.sql.Date dateOrdered = null;
    private java.sql.Date dateExpected = null;

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">
    /**
     * An order
     */
    public AnOrder() {
        this.primaryKey = SQLHelper.INVALID_PRIMARY_KEY;
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Setters & Getters">
    @Override
    public void setPrimaryKey(Integer aPrimaryKey)
            throws SQLException {
        this.primaryKey = HELPER.doNullCheck(SQLHelperOrder.COLUMN_PK, aPrimaryKey);
    }

    /**
     * This can not be null.
     *
     * @param aDescription
     * @throws SQLException
     */
    public void setDescription(String aDescription)
            throws SQLException {
        this.description = HELPER.doNullCheck(SQLHelperOrder.COLUMN_DESCRIPTION, aDescription);
    }

    /**
     * This can not be null.
     *
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * This can not be null.
     *
     * @param anOrderedFrom
     * @throws SQLException
     */
    public void setOrderedFrom(Integer anOrderedFrom)
            throws SQLException {
        this.orderedFrom = HELPER.doNullCheck(SQLHelperOrder.COLUMN_ORDEREDFROM, anOrderedFrom);
    }

    /**
     * This can not be null.
     *
     * @return
     */
    public Integer getOrderedFrom() {
        return this.orderedFrom;
    }

    /**
     * This can not be null.
     *
     * @param anOrderStatus
     * @throws SQLException
     */
    public void setOrderStatus(String anOrderStatus)
            throws SQLException {
        String tmpValue = HELPER.doNullCheck(SQLHelperOrder.COLUMN_ORDERSTATUS, anOrderStatus);
        this.orderStatus = OrderStatusType.getType(tmpValue);
    }

    /**
     * This can not be null.
     *
     * @param anOrderStatus
     * @throws SQLException
     */
    public void setOrderStatus(OrderStatusType anOrderStatus)
            throws SQLException {
        //Calls the overloaded method instead of directly setting so the null check can occur.
        setOrderStatus(anOrderStatus.getText());
    }

    /**
     * This can not be null.
     *
     * @return
     */
    public OrderStatusType getOrderStatus() {
        return this.orderStatus;
    }

    /**
     * This can not be null.
     *
     * @param aDateOrdered
     * @throws SQLException
     */
    public void setDateOrdered(java.sql.Date aDateOrdered)
            throws SQLException {
        this.dateOrdered = HELPER.doNullCheck(SQLHelperOrder.COLUMN_DATEORDERED, aDateOrdered);
    }

    /**
     * This can not be null.
     *
     * @return
     */
    public java.sql.Date getDateOrdered() {
        return (java.sql.Date) this.dateOrdered.clone();
    }

    /**
     * This can be null.
     *
     * @param aDateExpected
     * @throws SQLException
     */
    public void setDateExpected(java.sql.Date aDateExpected)
            throws SQLException {
        this.dateExpected = HELPER.doNullCheck(SQLHelperOrder.COLUMN_DATEEXPECTED, aDateExpected);
    }

    /**
     * This can be null.
     *
     * @return
     */
    public java.sql.Date getDateExpected() {
        if (this.dateExpected == null) {
            return null;
        } else {
            return (java.sql.Date) this.dateExpected.clone();
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Protected Methods">
    @Override
    protected boolean isAlreadyInDatabase() {
        boolean returnValue = false;

        try {
            if (this.primaryKey == null) {
                returnValue = false;
            } else {
                returnValue = (AnOrder.load(this.getPrimaryKey()) != null);
            }
        } catch (SQLException exSQL) {
        } catch (Exception ex) {
        }
        return returnValue;
        //return !(primaryKey.equals(SQLHelper.INVALID_PRIMARY_KEY));
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Public Static Methods">
    /**
     * Gets all the objects from the database.
     *
     * @return A list of objects with values loaded from the database.
     * @throws SQLException
     * @throws Exception
     */
    public static ArrayList<AnOrder> loadAll()
            throws SQLException, Exception {
        return HELPER.selectAll();
    }

    /**
     * Gets the object from the database.
     *
     * @param primaryKey The primary key in the table to retrieve.
     * @return The object with values loaded from the database, or a null object
     * if not found.
     * @throws SQLException
     * @throws Exception
     */
    public static AnOrder load(int primaryKey)
            throws SQLException, Exception {
        return HELPER.selectOne(primaryKey);
    }

    /**
     * Saves the specified object to the database.
     *
     * @param anObj The object to be saved.
     * @throws SQLException
     * @throws Exception
     */
    public static void save(AnOrder anObj)
            throws SQLException, Exception {
        if (anObj.isAlreadyInDatabase()) {
            HELPER.update(anObj);
        } else {
            HELPER.insert(anObj);
        }
    }

    /**
     * Removes the specific object from the database.
     *
     * @param anObj The object to be saved.
     * @throws SQLException
     * @throws Exception
     */
    public static void remove(AnOrder anObj)
            throws SQLException, Exception {
        remove(anObj.getPrimaryKey());
    }

    /**
     * Removes the specific primaryKey from the database.
     *
     * @param primaryKey The primary key to be deleted.
     * @throws SQLException
     * @throws Exception
     */
    public static void remove(Integer primaryKey)
            throws SQLException, Exception {
        HELPER.delete(primaryKey);
    }
    // </editor-fold>
}
