package trackit.DAL;

import java.sql.*;

/**
 * DAL Layer: Handles a single parameter for a stored procedure.
 *
 * @author Bond
 */
public abstract class SprocParameter {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    /**
     * The actual datatype of the parameter.
     */
    protected final int valueType; //Should never be changed in child classes.
    // </editor-fold>
    // <editor-fold defaultstate="expaned" desc="Private Fields">
    private String name = "";
    /**
     * The value of the parameter. Converted to a String for convenience.
     */
    private String value = "";
    private ParameterDirection direction = ParameterDirection.IN;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Can not create a default instance as the name and value fields are
     * required.
     *
     * @param aValueType The data type of the parameter as specified by the
     * Types class.
     */
    protected SprocParameter(int aValueType) {
        if (aValueType != Types.VARCHAR
                && aValueType != Types.INTEGER
                && aValueType != Types.DOUBLE
                && aValueType != Types.DATE) {
            throw new UnsupportedSQLTypeException(aValueType);
        }
        this.valueType = aValueType;
    }

    /**
     * Creates a new instance. Defaults valueType to String.class and direction
     * to IN.
     *
     * @param valueType The data type of the parameter as specified by the Types
     * class.
     * @param name The name of the parameter in the sproc.
     * @param value The value to pass to the sproc. Passing in null will cause
     * Types.NULL to be used.
     */
    protected SprocParameter(int valueType, String name, String value) {
        this(valueType);
        if (name == null) {
            throw new IllegalArgumentException("Parameter 'name' must not be null.");
        }
        this.name = name;
        this.value = value;
    }

    /**
     * Creates a new instance. Defaults valueType to String.class.
     *
     * @param name The name of the parameter in the sproc.
     * @param value The value to pass to the sproc.
     * @param valueType The data type of the parameter as specified by the Types
     * class.
     * @param direction The direction of the dataflow of the parameter as
     * specified by the ParameterMetaData.parameterMode* values.
     */
    protected SprocParameter(int valueType, String name, String value, ParameterDirection direction) {
        this(valueType, name, value);
        this.direction = direction;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    /**
     *
     * @return
     */
    public boolean isNull() {
        return (this.value == null);
    }

    /**
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return this.value;
    }

    /**
     *
     * @param aValue
     */
    public void setValue(String aValue) {
        this.value = aValue;
    }

    /**
     * The Types.* value of this parameter.
     *
     * @return The int representation of the Types.* value.
     */
    public int getValueType() {
        return this.valueType;
    }

    /**
     * The direction the data flows for this parameter.
     *
     * @return The direction of the parameter.
     */
    public ParameterDirection getDirection() {
        return this.direction;
    }
    // </editor-fold>  
}
