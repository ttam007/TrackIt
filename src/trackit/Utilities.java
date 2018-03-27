package trackit;

/**
 * A utility class for handling common constants and scalar functions.
 */
public class Utilities {

    public static final String PROGRAM_NAME = "TrackIt HITS";

    public static String getWindowCaption(String windowName) {
        return String.format("{0} - {1}", PROGRAM_NAME, windowName);
    }
}
