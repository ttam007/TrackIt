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

    // <editor-fold defaultstate="collapsed" desc="Constants-Labels">
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
     * Default caption for error dialog boxes.
     */
    public static final String ERROR_MSG_CAPTION = "Error";

    /**
     * Default text for OK buttons.
     */
    public static final String BUTTON_OK = "OK";

    /**
     * Default text for Cancel buttons.
     */
    public static final String BUTTON_CANCEL = "Cancel";

    /**
     * Default text for Cancel buttons.
     */
    public static final String BUTTON_CREATE = "Create";

    /**
     * Default text for Cancel buttons.
     */
    public static final String BUTTON_EDIT = "Edit";

    /**
     * Default text for Cancel buttons.
     */
    public static final String BUTTON_REMOVE = "Remove";

    /**
     * Default text for Add buttons.
     */
    public static final String BUTTON_ADD = "Add Item";

    /**
     * Default text for Check In buttons.
     */
    public static final String BUTTON_CHECKIN = "Check In";

    /**
     * Default text for Check In/Out buttons.
     */
    public static final String BUTTON_CHECKINOUT = "Check In/Out";

    /**
     * Default text for Check In All buttons.
     */
    public static final String BUTTON_CHECKINALL = "Check In All";

    /**
     * Default text for Login buttons.
     */
    public static final String BUTTON_LOGIN = "Login";

    /**
     * Default text for Logout buttons.
     */
    public static final String BUTTON_LOGOUT = "Logout";

    /**
     * Default text for Exit buttons.
     */
    public static final String BUTTON_EXIT = "Exit";

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constants-Other">
    /**
     * The correct format of all SQL dates.
     */
    public static final String SQL_DATE_FORMAT = "yyyy-MM-dd";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private static String errorMessage;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Can never create an instance of this class.
     */
    private Utilities() {
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Instance Methods">
    //There shouldn't be any public instance methods in this class.
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Public Static Methods">
    /**
     * Gets the caption of the window (frame/dialog) in a consistent formatting.
     *
     * @param windowName The name of the window.
     * @return The window's caption.
     */
    public static String getWindowCaption(String windowName) {
        return String.format("%s %s - %s", TEAM_NAME, PROGRAM_NAME_SHORT, windowName);
    }

    /**
     * Stores the generated error message for retrieval by another class.
     *
     * @param anErrorMessage The error message generated.
     */
    public static void setErrorMessage(String anErrorMessage) {
        Utilities.errorMessage = anErrorMessage;
    }

    /**
     * Stores the generated error message for retrieval by another class.
     *
     * @param ex The Exception error generated.
     */
    public static void setErrorMessage(Exception ex) {
        Utilities.errorMessage = ex.getLocalizedMessage();
    }

    /**
     * This will always have the last error message generated.
     *
     * @return The last error generated.
     */
    public static String getErrorMessage() {
        return Utilities.errorMessage;
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
        sb.append(String.format("Error = %s\r", ex.getLocalizedMessage()));
        sb.append(String.format("SQL State = %s\r", ex.getSQLState()));
        sb.append(String.format("Error Code = %s\r", ex.getErrorCode()));
        return sb.toString();
    }
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Public Static Methods - Dates">

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

    /**
     * Gets the current date from the specified date picker.
     *
     * @param aDatePicker The date picker to get the date from.
     * @return The date in the date picker.
     */
    public static java.util.Date getDatePickersDate(JDatePickerImpl aDatePicker) {
        UtilDateModel aModel = (UtilDateModel) aDatePicker.getModel();
        return aModel.getValue();
    }

    // </editor-fold>
}
