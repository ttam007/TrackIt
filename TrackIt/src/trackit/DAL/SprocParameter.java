package trackit.DAL;

import java.sql.*;

/**
 * DAL Layer: Handles a single parameter for a stored procedure.
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
     * @param valueType The data type of the parameter as specified by the Types
     * class.
     */
    protected SprocParameter(int valueType) {
        if (false) {
            throw new IllegalArgumentException("Parameter valueType must be from Types class.");
        }
        this.valueType = valueType;
    }

    /**
     * Creates a new instance. Defaults valueType to String.class and direction
     * to IN.
     *
     * @param valueType The data type of the parameter as specified by the Types
     * class.
     * @param name The name of the parameter in the sproc.
     * @param value The value to pass to the sproc.
     */
    protected SprocParameter(int valueType, String name, String value) {
        this(Types.VARCHAR);
        if (name == null || value == null) {
            throw new IllegalArgumentException("Parameters name and value must not be null.");
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
    // <*/editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Public Static Methods">
    /**
     * Sets the parameter value in the stored procedure. The assumption is that
     * the calling code will control the order the parameter is set in the
     * CallableStatment.
     *
     * @param stmt The Callable Statement (sproc) that that needs the value set.
     * @param aParam The parameter object that has the value to be used.
     * @throws SQLException
     */
    public static void setStatement(CallableStatement stmt, SprocParameter aParam)
            throws SQLException {
        switch (aParam.getValueType()) {
            case Types.DATE:
                stmt.setDate(aParam.getName(), Date.valueOf(aParam.getValue()));
                break;
            case Types.DECIMAL:
                Double aNumber = Double.valueOf(aParam.getValue());
                stmt.setBigDecimal(aParam.getName(), java.math.BigDecimal.valueOf(aNumber));
                break;
            case Types.INTEGER:
                stmt.setInt(aParam.getName(), Integer.valueOf(aParam.getValue()));
                break;
            case Types.VARCHAR:
                stmt.setString(aParam.getName(), aParam.getValue());
                break;
            default:
                break;
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    public boolean isNull() {
        return (this.value == null);
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

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