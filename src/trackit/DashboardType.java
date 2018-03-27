package trackit;

/**
 *
 */
public enum DashboardType {
    COUNT_ACTIVE_ORDERS(0), 
    COUNT_ITEMS_OUT_OF_STOCK(1), 
    COUNT_EXPIRED_ITEMS(2);

    private final int value;

    private static final String[] STATUS_TEXT = {
        "Count of Active Orders",
        "Count of Items Out of Stock",
        "Count of Expired Items"};

    private DashboardType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return STATUS_TEXT[this.value];
    }
}
