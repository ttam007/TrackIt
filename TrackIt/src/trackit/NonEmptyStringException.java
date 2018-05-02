package trackit;

/**
 * Handles negative amount exceptions.
 *
 * @author Bond
 */
public class NonEmptyStringException extends RuntimeException {

    // <editor-fold defaultstate="expanded" desc="Constants">
    private static final String MSG_TEMPLATE_GENERIC = "Text must not be empty.";
    private static final String MSG_TEMPLATE_SPECIFIC = "%s requires non-empty text.";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final String errorMessage;
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">

    /**
     * Use when a generic message is appropriate.
     */
    public NonEmptyStringException() {
        this.errorMessage = MSG_TEMPLATE_GENERIC;
    }

    /**
     * Use when a specific labeled component should be mentioned.
     *
     * @param labelText The label for the value that is empty.
     */
    public NonEmptyStringException(String labelText) {
        this.errorMessage = String.format(MSG_TEMPLATE_SPECIFIC, labelText);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public String getLocalizedMessage() {
        return errorMessage;
    }
    // </editor-fold>    
}
