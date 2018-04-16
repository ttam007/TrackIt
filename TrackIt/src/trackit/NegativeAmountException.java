package trackit;

/**
 * Handles negative amount exceptions.
 */
public class NegativeAmountException extends RuntimeException {

    // <editor-fold defaultstate="expanded" desc="Constants">
    private static final String MSG_TEMPLATE_GENERIC = "Amount is less than zero.";
    private static final String MSG_TEMPLATE_SPECIFIC = "$%f2 is less than zero.";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final String errorMessage;
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">

    /**
     * avoid negative values
     */
    public NegativeAmountException() {
        this.errorMessage = MSG_TEMPLATE_GENERIC;
    }

    /**
     *
     * @param amount
     */
    public NegativeAmountException(Float amount) {
        this.errorMessage = String.format(MSG_TEMPLATE_SPECIFIC, amount);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public String getLocalizedMessage() {
        return errorMessage;
    }
    // </editor-fold>    
}
