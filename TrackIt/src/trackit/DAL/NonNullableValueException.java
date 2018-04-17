package trackit.DAL;

import java.sql.*;

/**
 * Handles null value exceptions.
 *
 * @author Bond
 */
public class NonNullableValueException
        extends SQLException {

    // <editor-fold defaultstate="expanded" desc="Constants">
    private static final String MSG_TEMPLATE_GENERIC = "Null value is not allowed.";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final String errorMessage;

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">
    /**
     * no null value
     */
    public NonNullableValueException() {
        this.errorMessage = MSG_TEMPLATE_GENERIC;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public String getLocalizedMessage() {
        return errorMessage;
    }
    // </editor-fold>    
}
