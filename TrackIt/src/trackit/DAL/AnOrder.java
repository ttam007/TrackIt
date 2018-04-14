package trackit.DAL;

import java.sql.*;
import java.util.*;
import trackit.*;

/**
 * DAL Layer: Handles all aspects of a single Order.
 *
 * @author Bond
 */
public class AnOrder
        extends DatabaseObject {

    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private static final SQLHelperOrder HELPER = new SQLHelperOrder();
    private String description;
    /**
     * FK to suppliers.supplierId
     */
    private Integer orderedFrom;
    private OrderStatusType orderStatus;
    private java.sql.Date dateOrdered;
    private java.sql.Date dateExpected;

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">

    /**
     *An order
     */
    public AnOrder() {
        this.primaryKey = SQLHelper.INVALID_PRIMARY_KEY;
    }

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Setters & Getters">

    /**
     *
     * @param description
     * @throws SQLException
     */
    public void setDescription(String description)
            throws SQLException {
        this.description = HELPER.doNullCheck(SQLHelperOrder.COLUMN_DESCRIPTION, description);
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     *
     * @param orderedFrom
     * @throws SQLException
     */
    public void setOrderedFrom(Integer orderedFrom)
            throws SQLException {
        this.orderedFrom = HELPER.doNullCheck(SQLHelperOrder.COLUMN_ORDEREDFROM, orderedFrom);
    }

    /**
     *
     * @return
     */
    public Integer getOrderedFrom() {
        return this.orderedFrom;
    }

    /**
     *
     * @param orderStatus
     * @throws SQLException
     */
    public void setOrderStatus(String orderStatus)
            throws SQLException {
        String tmpValue = HELPER.doNullCheck(SQLHelperOrder.COLUMN_ORDERSTATUS, orderStatus);
        this.orderStatus = OrderStatusType.getType(tmpValue);
    }

    /**
     *
     * @param orderStatus
     * @throws SQLException
     */
    public void setOrderStatus(OrderStatusType orderStatus)
            throws SQLException {
        //Calls the overloaded method instead of directly setting so the null check can occur.
        setOrderStatus(orderStatus.getText());
    }

    /**
     *
     * @return
     */
    public OrderStatusType getOrderStatus() {
        return this.orderStatus;
    }

    /**
     *
     * @param dateOrdered
     * @throws SQLException
     */
    public void setDateOrdered(java.sql.Date dateOrdered)
            throws SQLException {
        this.dateOrdered = HELPER.doNullCheck(SQLHelperOrder.COLUMN_DATEORDERED, dateOrdered);
    }

    /**
     *
     * @return
     */
    public java.sql.Date getDateOrdered() {
        return (java.sql.Date) this.dateOrdered.clone();
    }

    /**
     *
     * @param dateExpected
     * @throws SQLException
     */
    public void setDateExpected(java.sql.Date dateExpected)
            throws SQLException {
        this.dateExpected = HELPER.doNullCheck(SQLHelperOrder.COLUMN_DATEEXPECTED, dateExpected);
    }

    /**
     *
     * @return
     */
    public java.sql.Date getDateExpected() {
        return (java.sql.Date) this.dateExpected.clone();
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
