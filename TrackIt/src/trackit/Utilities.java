package trackit;

import java.sql.*;
import java.text.*;
import java.util.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

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

    /**
     * Default caption for error dialog boxes.
     */
    public static final String ERROR_MSG_CAPTION = "Error";

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
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Static Methods - Dates">

    /**
     * Converts from a standard java Date to a SQL Date class.
     *
     * @param aDate The date to be converted.
     * @return A standard java Date object with the same date as parameter
     * aDate.
     */
    public static java.sql.Date convertToSQLDate(java.util.Date aDate) {
        if (aDate == null) {
            return null;
        } else {
            return new java.sql.Date(aDate.getTime());
        }
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
        if (aValue == null) {
            return null;
        } else {
            return convertToSQLDate(convertToUtilDate(aValue));
        }
    }

    /**
     * Converts from a SQL Date to a standard java Date class.
     *
     * @param aDate The date to be converted.
     * @return A SQL Date object with the same date as parameter aDate.
     */
    public static java.util.Date convertToUtilDate(java.sql.Date aDate) {
        if (aDate == null) {
            return null;
        } else {
            return new java.util.Date(aDate.getTime());
        }
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
        if (aValue == null) {
            return null;
        } else {
            return getDateFormatter().parse(aValue);
        }
    }

    /**
     * This dateFormatter must be used so all dates are in standard SQL date
     * format. Must keep in method since public static field is not thread safe.
     *
     * @return
     */
    public static SimpleDateFormat getDateFormatter() {
        return new SimpleDateFormat(SQL_DATE_FORMAT);
    }

    /**
     * Since the Date class is depreciated for many of its methods, use this to
     * get a Calendar object set to the same date as the specified Date class
     * instance.
     *
     * @param aDate An instance of the Date class with a specific date.
     * @return A Calendar instance set to the specified date; however, if aDate
     * parameter is null, then null is returned.
     */
    public static Calendar getCalendarWithDate(java.util.Date aDate) {
        Calendar aCalendar = null;
        if (aDate != null) {
            aCalendar = new GregorianCalendar();
            aCalendar.setTime(aDate);
        }
        return aCalendar;
    }

    /**
     * Creates a date picker object.
     *
     * @return The date picker object.
     */
    public static JDatePickerImpl getDatePicker() {
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        UtilDateModel expModel = new UtilDateModel();
        expModel.setSelected(true);

        JDatePanelImpl aDatePanel = new JDatePanelImpl(expModel, p);
        return new JDatePickerImpl(aDatePanel, new DateLabelFormatter());
    }

    /**
     * Sets the specified date in the specified date picker.
     *
     * @param aDatePicker The date picker to set the date in.
     * @param aDate The date to be set in the date picker.
     */
    public static void setDatePickersDate(JDatePickerImpl aDatePicker, java.util.Date aDate) {
        setDatePickersDate(aDatePicker, Utilities.convertToSQLDate(aDate));
    }

    /**
     * Sets the specified date in the specified date picker.
     *
     * @param aDatePicker The date picker to set the date in.
     * @param aDate The date to be set in the date picker.
     */
    public static void setDatePickersDate(JDatePickerImpl aDatePicker, java.sql.Date aDate) {
        if (aDate == null) {
            aDatePicker.getModel().setValue(null);
        } else {
            Calendar aCalendar = Utilities.getCalendarWithDate(aDate);
            aDatePicker.getModel().setDate(aCalendar.get(Calendar.YEAR), aCalendar.get(Calendar.MONTH), aCalendar.get(Calendar.DAY_OF_MONTH));
        }
    }
}
