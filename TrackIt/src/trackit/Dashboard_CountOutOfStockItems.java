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
    /**
     * Default Constructor.
     */
    public Dashboard_CountOutOfStockItems() {
        super(DashboardType.COUNT_ITEMS_OUT_OF_STOCK);

        this.description = PREFIX + "%d item(s) are out of stock.";
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Calculates the number of inventory items that are out of stock from a
     * list of inventory items.
     *
     * @param aList The list of Inventory Items.
     */
    private void getNumOfItemsOutOfStock(ArrayList<AnInventoryItem> aList) {
        int counter = 0;
        for (AnInventoryItem item : aList) {
            if (item.getQuantity().equals(0)) {
                counter += 1;
            }
        }
        this.count = counter;
    }

    @Override
    protected boolean refreshData() {
        boolean isSuccessful = false;

        try {
            if (bllInventory.load()) {
                ArrayList<AnInventoryItem> aList = bllInventory.getList();
                getNumOfItemsOutOfStock(aList);
            }
            isSuccessful = true;
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
        }

        return isSuccessful;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public String getData() {
        if (refreshData()) {
            return String.format(this.description, this.count);
        } else {
            return "";
        }
    }
    // </editor-fold>
}
