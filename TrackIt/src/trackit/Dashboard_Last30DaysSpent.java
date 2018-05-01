package trackit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The dashboard widget for showing the money spent in the last 30 days.
 *
 * @author Bond
 */
public class Dashboard_Last30DaysSpent
        extends Dashboard {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private Double money;
    private final Orders bllOrders = new Orders();
    private final OrderItems bllOrderItems = new OrderItems();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Default Constructor.
     */
    public Dashboard_Last30DaysSpent() {
        super(DashboardType.MONEY_SPENT_LAST_30_DAYS);

        this.description = PREFIX + "In the last 30 days, you have spent %s.";
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Gets the total extended price of the specified order.
     *
     * @param orderPrimaryKey The primary key of the order to be summed.
     * @param orderItemList The complete list of order items in the database.
     * @return The sum of the extended price of all items in the order.
     */
    private Double searchOrderItem(Integer orderPrimaryKey) {
        Double moneyCount = 0.00;

        ArrayList<AnOrderItem> orderItemList = new ArrayList<>();
        if (bllOrderItems.loadByOrder(orderPrimaryKey)) {
            orderItemList = bllOrderItems.getList();
        }

        for (AnOrderItem orderItem : orderItemList) {
            if (orderItem.getOrderId().equals(orderPrimaryKey)) {
                moneyCount += orderItem.getExtendedPrice();
            }
        }
        return moneyCount;
    }

    private void countMoney(ArrayList<AnOrder> aList) {
        Double moneyCount = 0.00;

        if (aList.size() > 0) {
            Date today = Utilities.getToday();
            Calendar minus30Cal = Calendar.getInstance();
            minus30Cal.add(Calendar.DAY_OF_MONTH, -30);
            Date minus30 = Utilities.removeTimeFromDate(minus30Cal);

            for (AnOrder order : aList) {
                Date dateToCompare = order.getDateOrdered();
                if (dateToCompare != null
                        && dateToCompare.after(minus30)
                        && (dateToCompare.compareTo(today) <= 0)) {
                    moneyCount += searchOrderItem(order.getPrimaryKey());
                }
            }
        }
        this.money = moneyCount;
    }

    @Override
    protected boolean refreshData() {
        boolean isSuccessful = false;

        try {
            if (bllOrders.load()) {
                ArrayList<AnOrder> aList = bllOrders.getList();
                countMoney(aList);
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
            return String.format(this.description, Utilities.formatAsCurrency(this.money));
        } else {
            return "";
        }
    }
    // </editor-fold>
}
