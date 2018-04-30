package trackit;

import java.util.ArrayList;
import java.util.Date;

/**
 * The dashboard widget for showing the next expired item.
 *
 * @author Bond
 */
public class Dashboard_NextExpiredItem
        extends Dashboard {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private Date date;
    private final Inventory bllInventory = new Inventory();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Dashboard_NextExpiredItem() {
        super(DashboardType.DATE_NEXT_ITEM_EXPIRES);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    private void getDateNextExpires(ArrayList<AnInventoryItem> aList) {
        Date min = new Date(Long.MAX_VALUE);
        Date today = Utilities.getToday();

        if (aList != null) {
            for (AnInventoryItem item : aList) {
                Date dateToCompare = item.getExpirationDate();
                if (dateToCompare != null
                        && dateToCompare.before(min)
                        && (dateToCompare.compareTo(today) >= 0)) {
                    min = dateToCompare;
                }
            }
        }
        this.date = (min.getTime() == Long.MAX_VALUE ? null : min);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    @Override
    public boolean refreshData() {
        boolean isSuccessful = false;

        try {
            if (bllInventory.load()) {
                ArrayList<AnInventoryItem> aList = bllInventory.getList();
                getDateNextExpires(aList);
                this.description = "The next item to expire will be on ";
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
        return PREFIX + this.description + this.date.toString();
    }
    // </editor-fold>
}
