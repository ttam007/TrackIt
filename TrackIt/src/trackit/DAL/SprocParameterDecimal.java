package trackit.DAL;

import java.sql.*;

/**
 * DAL Layer: Handles a single DECIMAL parameter for a stored procedure.
 */
public class SprocParameterDecimal
        extends SprocParameter {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    protected int precision;
    protected int scale;

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Constructors">
    /**
     * Creates a new instance. Sets valueType to Types.DECIMAL.
     *
     * @param name The name of the parameter in the sproc.
     * @param value The value to pass to the sproc.
     * @param direction The direction of the dataflow of the parameter as
     * specified by the ParameterMetaData.parameterMode* values.
     * @param precision The total number of digits.
     * @param scale The number of digits to the right of the decimal point.
     */
    public SprocParameterDecimal(String name, String value, ParameterDirection direction,
            int precision, int scale) {
        super(Types.DECIMAL, name, value, direction);
        this.precision = precision;
        this.scale = scale;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    // </editor-fold>
}
