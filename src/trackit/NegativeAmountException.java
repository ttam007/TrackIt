package trackit;

/**
 * Handles negative amount exceptions.
 */
public class NegativeAmountException extends RuntimeException {

    // <editor-fold defaultstate="expanded" desc="Constants">
    private final String MSG_TEMPLATE_BASE = "Amount is less than zero.";
    //private final String MSG_TEMPLATE_SYMBOL = "Values \"%d\" and \"%d\" are out of order.";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final String errorMessage;
    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Constructors">
    public NegativeAmountException() {
        this.errorMessage = MSG_TEMPLATE_BASE;
    }

    /*public NegativeAmountException(int value1, int value2) {
        this.errorMessage = String.format(MSG_TEMPLATE_BASE + "  " + MSG_TEMPLATE_SYMBOL, value1, value2);
    }*/

    // </editor-fol + "Error = " + super.>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public String getLocalizedMessage() {
        return errorMessage;
    }
    // </editor-fold>    
}
