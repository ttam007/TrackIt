package trackit;

import java.sql.*;

/**
 * This is a static class that only has global constants and "fire and forget"
 * static methods.
 */
public class Utilities {

    public static final String TEAM_NAME = "TrackIt";
    public static final String PROGRAM_NAME_SHORT = "HITS";
    public static final String PROGRAM_NAME_LONG = "Home Inventory Tracking System";

    /**
     * Can never create an instance of this class.
     */
    private Utilities() {
    }

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
        sb.append(String.format("Error = %s\r", ex.getLocalizedMessage()));
        sb.append(String.format("SQL State = %s\r", ex.getSQLState()));
        sb.append(String.format("Error Code = %s\r", ex.getErrorCode()));
        return sb.toString();
    }
}
