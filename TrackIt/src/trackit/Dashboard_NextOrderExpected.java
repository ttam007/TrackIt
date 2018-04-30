package trackit;

import java.util.ArrayList;
import java.util.Date;

/**
 * The dashboard widget for showing when the next order is expected to arrive.
 *
 * @author Bond
 */
public class Dashboard_NextOrderExpected
        extends Dashboard {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private Date date;
    private final Orders bllOrders = new Orders();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Default Constructor.
     */
    public Dashboard_NextOrderExpected() {
        super(DashboardType.DATE_NEXT_ORDER_ARRIVES);

        this.description = PREFIX + "The next order will arrive on %s.";
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    private void getDateNextArrives(ArrayList<AnOrder> aList) {
        Date min = new Date(Long.MAX_VALUE);
        Date today = Utilities.getToday();

        if (aList != null) {
            for (AnOrder item : aList) {
                Date dateToCompare = item.getDateExpected();
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
    protected boolean refreshData() {
        boolean isSuccessful = false;

        try {
            if (bllOrders.load()) {
                ArrayList<AnOrder> aList = bllOrders.getList();
                getDateNextArrives(aList);
            }
            isSuccessful = true;
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
        }

        return isSuccessful;
    }

    @Override
    public String getData() {
        if (refreshData()) {
            return String.format(this.description, this.date);
        } else {
            return "";
        }
    }
    // </editor-fold>
}
