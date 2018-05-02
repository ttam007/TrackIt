package trackit.DAL;

import java.sql.*;

/**
 * DAL Layer: Handles a single DATE parameter for a stored procedure.
 *
 * @author Bond
 */
public class SprocParameterDate
        extends SprocParameter {

    // <editor-fold defaultstate="expanded" desc="Constructors">
    /**
     * Creates a new instance. Sets valueType to Types.DATE.
     *
     * @param name The name of the parameter in the sproc.
     * @param value The value to pass to the sproc.
     * @param direction The direction of the dataflow of the parameter as
     * specified by the ParameterMetaData.parameterMode* values.
     */
    public SprocParameterDate(String name, String value, ParameterDirection direction) {
        super(Types.DATE, name, value, direction);
    }
    // </editor-fold>
}
