package trackit.DAL;

import java.sql.*;

/**
 * DAL Layer: Converts a row in database table Items into an AnItem object and
 * vice versa. Everything about the ItemId primary key will be handled in child
 * classes.
 *
 * @author Bond
 */
public class SQLHelperItem {

    // <editor-fold defaultstate="collapsed" desc="Database Columns">
    /**
     *
     */
    public static final String COLUMN_DESCRIPTION = "description";

    /**
     *
     */
    public static final String COLUMN_SKU = "sku";

    /**
     *
     */
    public static final String COLUMN_SIZEUNIT = "sizeUnit";

    /**
     *
     */
    public static final String COLUMN_ITEMSTATUS = "itemStatus";

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default Constructor.
     */
    public SQLHelperItem() {

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Checks to see if any column that is not nullable has a null value.
     *
     * @param columnName The column to check.
     * @param aValue The value to check.
     * @return Either the parameter aValue or throws NonNullableValueException.
     * @throws SQLException When aValue is NULL and NULL is not allowed.
     */
    public String doNullCheck(String columnName, String aValue)
            throws SQLException {
        if (aValue == null
                && (columnName.equalsIgnoreCase(COLUMN_DESCRIPTION)
                || columnName.equalsIgnoreCase(COLUMN_SKU)
                || columnName.equalsIgnoreCase(COLUMN_SIZEUNIT)
                || columnName.equalsIgnoreCase(COLUMN_ITEMSTATUS))) {
            throw new NonNullableValueException();
        } else {
            return aValue;
        }
    }
    // </editor-fold>
}
