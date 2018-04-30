package trackit;

import java.util.ArrayList;

/**
 * The dashboard widget for showing the count of out of stock items.
 *
 * @author Bond
 */
public class Dashboard_CountOutOfStockItems
        extends Dashboard {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private Integer count;
    private final Inventory bllInventory = new Inventory();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Dashboard_CountOutOfStockItems() {
        super(DashboardType.COUNT_ITEMS_OUT_OF_STOCK);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    private void getNumOfItemsOutOfStock(ArrayList<AnInventoryItem> aList) {
        int counter = 0;
        for (AnInventoryItem item : aList) {
            if (item.getQuantity().equals(0)) {
                counter += 1;
            }
        }
        this.count = counter;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    @Override
    public boolean refreshData() {
        boolean isSuccessful = false;

        try {
            if (bllInventory.load()) {
                ArrayList<AnInventoryItem> aList = bllInventory.getList();
                getNumOfItemsOutOfStock(aList);
                this.description = " item(s) are out of stock";
            }
            isSuccessful = true;
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
        }

        return isSuccessful;
    }

    @Override
    public String toString() {
        //TODO
        return PREFIX + this.count + this.description;
    }
// </editor-fold>
}
