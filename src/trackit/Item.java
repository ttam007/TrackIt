package trackit;

/**
 * Handles all things related to a single Item.
 */
public class Item {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private String description;
    private String sku;
    private Float size;
    private String sizeUnit; //Should this be an enum?
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Item() {

    }

    public Item(String description, String sku, Float size, String sizeUnit) {
        this();
        
        this.description = description;
        this.sku = sku;
        this.size = size;
        this.sizeUnit = sizeUnit;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    // </editor-fold>  
}
