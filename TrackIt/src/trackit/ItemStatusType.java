/*
 * Decompiled with CFR 0_123.
 */
package trackit;

public enum ItemStatusType {
    // <editor-fold defaultstate="expanded" desc="Enumeration">
    AVAILABLE(0),
    DISCONTINUED(1),
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
    public int getValue() {
        return this.value;
    }

    public String getText() {
        return STATUS_TEXT[this.value];
    }

    static {
        //TODO:  get values from database.
        STATUS_TEXT = new String[]{"Available", "Discontinued", "Do Not Order"};
    }
    // </editor-fold>
}
