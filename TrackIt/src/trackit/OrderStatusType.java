package trackit;

/**
 * All the statuses for an order.
 *
 * @author Bond
 */
public enum OrderStatusType {
    // <editor-fold defaultstate="expanded" desc="Enumeration">

    /**
     *
     */
    CREATED(0),
    /**
     *
     */
    ORDERED(1),
    /**
     *
     */
    SHIPPED(2),
    /**
     *
     */
    DELIVERED(3);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final int value;
    private static final String[] STATUS_TEXT;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    private OrderStatusType(int value) {
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

    /**
     *
     * @return
     */
    public String getText() {
        return STATUS_TEXT[this.value];
    }

    static {
        //TODO:  get values from database.
        STATUS_TEXT = new String[]{"Created", "Ordered", "Shipped", "Delivered"};
    }

    /**
     * Gets the enumerated value from its string equivalent.
     *
     * @param aValue The string equivalent of the enumerated value.
     * @return The enumerated value.
     */
    public static OrderStatusType getType(String aValue) {
        for (OrderStatusType aType : OrderStatusType.values()) {
            if (aType.getText().equalsIgnoreCase(aValue)) {
                return aType;
            }
        }
        return null;
    }
    // </editor-fold>
}
