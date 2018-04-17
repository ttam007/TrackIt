package trackit.DAL;

/**
 * DAL Layer: Used to determine the direction of a stored procedure's parameter.
 * Integer conversions match ParameterMetaData.parameterMode* values.
 *
 * @author Bond
 */
public enum ParameterDirection {
    // <editor-fold defaultstate="expanded" desc="Enumeration">
    /**
     * parameterModeUnknown = 0;
     */
    UNKNOWN(0),
    /**
     * parameterModeIn = 1;
     */
    IN(1),
    /**
     * parameterModeInOut = 2;
     */
    INOUT(2),
    /**
     * parameterModeOut = 4;
     */
    OUT(4);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final int value;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    private ParameterDirection(int value) {
        this.value = value;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     *
     * @return
     */
    public int getValue() {
        return this.value;
    }
    // </editor-fold>
}
