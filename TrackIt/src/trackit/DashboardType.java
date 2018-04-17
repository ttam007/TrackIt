package trackit;

/**
 *
 * @author Bryan
 */
public enum DashboardType {
    // <editor-fold defaultstate="expanded" desc="Enumeration">

    /**
     *
     */
    COUNT_ITEMS_OUT_OF_STOCK(0),
    /**
     *
     */
    DATE_NEXT_ITEM_EXPIRES(1),
    /**
     *
     */
    DATE_NEXT_ORDER_ARRIVES(2),
    /**
     *
     */
    MONEY_SPENT_LAST_30_DAYS(3);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final int value;
    private static final String[] STATUS_TEXT;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    private DashboardType(int value) {
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
        STATUS_TEXT = new String[]{
            "Count of Items Out of Stock",
            "Date Next Item Expires",
            "Date Next Order Arrives",
            "Money Spent in the Last 30 Days"
        };
    }
    // </editor-fold>
}
