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
    private static final String MSG_TEMPLATE_SPECIFIC_TYPE = "Data Type %s from Types class is not supported.";
    private static final String MSG_TEMPLATE_SPECIFIC_CLASS = "Data Type %s from Types class is not supported in class %s.";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final String errorMessage;

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">
    /**
     * Use for a generic message.
     */
    public UnsupportedSQLTypeException() {
        this.errorMessage = MSG_TEMPLATE_GENERIC;
    }

    /**
     * Use for a message that specifies the type that is unsupported.
     *
     * @param aType A value from the Types class that was attempted to be used.
     */
    public UnsupportedSQLTypeException(int aType) {
        this.errorMessage = String.format(MSG_TEMPLATE_SPECIFIC_TYPE, getTypeName(aType));
    }

    /**
     * Use for a message that specifies the type that is unsupported and the
     * class that was trying to use it.
     *
     * @param aType A value from the Types class that was attempted to be used.
     * @param aClass The Class that the Type was attempted to be used in.
     */
    public UnsupportedSQLTypeException(int aType, Class aClass) {
        this.errorMessage = String.format(MSG_TEMPLATE_SPECIFIC_CLASS, getTypeName(aType), aClass.getName());
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Converts the specified Type in a meaningful string.
     *
     * @param aType A value from the Types class to convert.
     * @return The name of the specified type.
     */
    private String getTypeName(int aType) {
        return String.valueOf(aType);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public String getLocalizedMessage() {
        return errorMessage;
    }
    // </editor-fold>    
}
