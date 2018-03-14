package trackit;

/**
 * A list of the types of order statuses. Also handles everything else specific
 * to each status.
 */
public enum OrderStatus {
    // <editor-fold defaultstate="expanded" desc="Enumeration">
    ORDERED(0),
    BEINGSHIPPED(1),
    DELIVERED(2);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final int value;

    /**
     * Contains the text for each enumerated value. Index position must match
     * enumeration's value.
     */
    private static final String[] STATUS_TEXT = {
        "Ordered", "Beging Shipped", "Delievered"
    };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    private OrderStatus(int value) {
        this.value = value;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Gets the index for each status.
     *
     * @return The index.
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets the text for each status.
     *
     * @return The text.
     */
    public String getText() {
        return STATUS_TEXT[this.value];
    }
    // </editor-fold>
}
