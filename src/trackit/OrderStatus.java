package trackit;

public enum OrderStatus {

    ORDERED(0), BEINGSHIPPED(1), DELIVERED(2);

    private final int value;

    private static final String[] STATUS_TEXT = { "Ordered", "Beging Shipped", "Delievered" };

    private OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return STATUS_TEXT[this.value];
    }
}
