package trackit;

import trackit.DAL.SQLHelperSuppliers;
import java.util.*;

/**
 *
 */
public class BALController {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    /**
     * The singleton instance of this class.
     */
    private static BALController singleton = null;

    private final ArrayList<Order> orders = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();
    private final ArrayList<Supplier> suppliers = new ArrayList<>();
    private final Inventory inventory = new Inventory();

    private SQLHelperSuppliers sqlSupplier = new SQLHelperSuppliers();

    // </editor-fold>        
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * A private constructor to create a singleton.
     */
    private BALController() {

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Static Methods">
    /**
     * Gets the instance of the singleton object.
     *
     * @return The singleton.
     */
    public static BALController getInstance() {
        if (BALController.singleton == null) {
            BALController.singleton = new BALController();
        }
        return BALController.singleton;
    }

    // </editor-fold>
}
