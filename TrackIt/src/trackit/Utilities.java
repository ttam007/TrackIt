package trackit;

import java.sql.*;
import java.text.*;

/**
 * This is a static class that only has global constants and "fire and forget"
 * static methods.
 *
 * @author Bond
 */
public class Utilities {

    // <editor-fold defaultstate="expanded" desc="Constants">
    /**
     * The name of the team that created this program.
     */
    public static final String TEAM_NAME = "TrackIt";

    /**
     * The abbreviated name of this program.
     */
    public static final String PROGRAM_NAME_SHORT = "HITS";

    /**
     * The long name of this program.
     */
    public static final String PROGRAM_NAME_LONG = "Home Inventory Tracking System";

    /**
     * The correct format of all SQL dates.
     */
    public static final String SQL_DATE_FORMAT = "yyyy-MM-dd";

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    //There shouldn't be any private fields in this class.
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Can never create an instance of this class.
     */
    private Utilities() {
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    //There shouldn't be any public instance methods in this class.
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Static Methods">
    /**
     *
     * @param windowName
     * @return
     */
    public static String getWindowCaption(String windowName) {
        return String.format("%s %s - %s", TEAM_NAME, PROGRAM_NAME_SHORT, windowName);
    }

    /**
     * Used to convert a SQLException into a single string for displaying to the
     * User.
     *
     * @param ex The SQLException to be converted.
     * @return The complete error message for displaying to the User.
     */
    public static String buildErrorMessage(SQLException ex) {
        StringBuilder sb = new StringBuilder();
        //sb.append(String.format("Error = %s\r", ex.getLocalizedMessage()));
        sb.append(String.format("SQL State = %s\r", ex.getSQLState()));
        sb.append(String.format("Error Code = %s\r", ex.getErrorCode()));
        return sb.toString();
    }

    /**
     * Converts from a standard java Date to a SQL Date class.
     *
     * @param aDate The date to be converted.
     * @return A standard java Date object with the same date as parameter
     * aDate.
     */
    public static java.sql.Date convertToSQLDate(java.util.Date aDate) {
        return new java.sql.Date(aDate.getTime());
    }

    /**
     * Converts a string representation of a date into a Date object.
     *
     * @param aValue The value to convert to a Date object.
     * @return A Date object with the specified date.
     * @throws ParseException
     */
    public static java.sql.Date convertToSQLDate(String aValue)
            throws ParseException {
        return convertToSQLDate(convertToUtilDate(aValue));
    }

    /**
     * Converts from a SQL Date to a standard java Date class.
     *
     * @param aDate The date to be converted.
     * @return A SQL Date object with the same date as parameter aDate.
     */
    public static java.util.Date convertToUtilDate(java.sql.Date aDate) {
        return new java.util.Date(aDate.getTime());
    }

    /**
     * Converts a string representation of a date into a Date object.
     *
     * @param aValue The value to convert to a Date object.
     * @return A Date object with the specified date.
     * @throws ParseException
     */
    public static java.util.Date convertToUtilDate(String aValue)
            throws ParseException {
        return getDateFormatter().parse(aValue);
    }

    /**
     * This dateFormatter must be used so all dates are in standard SQL date
     * format. Must keep in method since public static field is not thread safe.
     *
     * @return
     */
    public static DateFormat getDateFormatter() {
        return new SimpleDateFormat(SQL_DATE_FORMAT);
    }
}
