package trackit.DAL;

import java.sql.*;

/**
 * DAL Layer: Handles a single INTEGER parameter for a stored procedure.
 *
 * @author Bond
 */
public class SprocParameterInteger
        extends SprocParameter {

    // <editor-fold defaultstate="expanded" desc="Constructors">
    /**
     * Creates a new instance. Sets valueType to Types.INTEGER.
     *
     * @param name The name of the parameter in the sproc.
     * @param value The value to pass to the sproc.
     * @param direction The direction of the dataflow of the parameter as
     * specified by the ParameterMetaData.parameterMode* values.
     */
    public SprocParameterInteger(String name, String value, ParameterDirection direction) {
        super(Types.INTEGER, name, value, direction);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    // </editor-fold>
}
