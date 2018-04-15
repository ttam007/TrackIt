package trackit;

/**
 * All the statuses for an item.
 *
 * @author Bond
 */
public enum ItemStatusType {
    // <editor-fold defaultstate="expanded" desc="Enumeration">

    /**
     *
     */
    AVAILABLE(0),
    /**
     *
     */
    DISCONTINUED(1),
    /**
     *
     */
    DO_NOT_ORDER(2);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final int value;
    private static final String[] STATUS_TEXT;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    private ItemStatusType(int value) {
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
        STATUS_TEXT = new String[]{"Available", "Discontinued", "Do Not Order"};
    }

    /**
     * Gets the enumerated value from its string equivalent.
     *
     * @param aValue The string equivalent of the enumerated value.
     * @return The enumerated value.
     */
    public static ItemStatusType getType(String aValue) {
        for (ItemStatusType aType : ItemStatusType.values()) {
            if (aType.getText().equalsIgnoreCase(aValue)) {
                return aType;
            }
        }
        return null;
    }
    // </editor-fold>
}
