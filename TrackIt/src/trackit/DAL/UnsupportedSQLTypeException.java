package trackit.DAL;

/**
 * Handles unsupported SQL data type exceptions.
 *
 * @author Bond
 */
public class UnsupportedSQLTypeException
        extends IllegalArgumentException {

    // <editor-fold defaultstate="expanded" desc="Constants">
    private static final String MSG_TEMPLATE_GENERIC = "Data type must be a supported type from the Types class.";
    private static final String MSG_TEMPLATE_SPECIFIC = "Data Type %s from Types class is not supported.";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final String errorMessage;

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">
    /**
     * exception
     */
    public UnsupportedSQLTypeException() {
        this.errorMessage = MSG_TEMPLATE_GENERIC;
    }

    /**
     *
     * @param aType A value from the Types class.
     */
    public UnsupportedSQLTypeException(int aType) {
        String typeName = String.valueOf(aType);
        this.errorMessage = String.format(MSG_TEMPLATE_GENERIC + "%n" + MSG_TEMPLATE_SPECIFIC, typeName);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public String getLocalizedMessage() {
        return errorMessage;
    }
    // </editor-fold>    
}
