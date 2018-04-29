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
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final int value;
    private static final String[] STATUS_TEXT = new String[]{
        "Created",
        "Ordered",
        "Shipped",
        "Delivered"};

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    private OrderStatusType(int value) {
        this.value = value;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Gets the Integer equivalent of the enumerated value.
     *
     * @return The Integer equivalent of the enumerated value.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Gets the String equivalent of the enumerated value.
     *
     * @return The String equivalent of the enumerated value.
     */
    @Override
    public String toString() {
        return STATUS_TEXT[this.value];
    }

    /**
     * Gets the array of all String equivalent values for this enumeration.
     *
     * @return
     */
    public static String[] getTextForAll() {
        return STATUS_TEXT.clone();
    }

    /**
     * Gets the enumerated value from its string equivalent.
     *
     * @param aValue The string equivalent of the enumerated value.
     * @return The enumerated value.
     */
    public static OrderStatusType getType(String aValue) {
        for (OrderStatusType aType : OrderStatusType.values()) {
            if (aType.toString().equalsIgnoreCase(aValue)) {
                return aType;
            }
        }
        return null;
    }
    // </editor-fold>
}
