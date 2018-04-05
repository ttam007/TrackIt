package trackit;

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
}
