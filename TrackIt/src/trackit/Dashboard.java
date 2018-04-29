package trackit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Bryan
 */
public class Dashboard {

    private final DashboardType type;

    //private String title;
    private Integer count;
    private Date date;
    private Double money;
    private String description;
    private Inventory bllInventory;
    private Orders bllOrders;

    /**
     *
     * @param type
     */
    public Dashboard(DashboardType type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public DashboardType getType() {
        return type;
    }

    /**
     * Refreshes the data for the dashboard.
     *
     * @return True = successfully refreshed; False = there was an error.
     */
    public boolean getData() {
        boolean isSuccessful = false;

        try {
            if (this.type == DashboardType.COUNT_ITEMS_OUT_OF_STOCK
                    || this.type == DashboardType.DATE_NEXT_ITEM_EXPIRES) {
                bllInventory = new Inventory();
                if (bllInventory.load()) {
                    ArrayList<AnInventoryItem> aList = bllInventory.getList();
                    switch (this.type) {
                        case COUNT_ITEMS_OUT_OF_STOCK:
                            getNumOfItemsOutOfStock(aList);
                            this.description = " item(s) out of stock";
                            break;
                        case DATE_NEXT_ITEM_EXPIRES:
                            getDateNextExpires(aList);
                            this.description = "The next item to expire will be on ";
                            break;
                        default:
                            break;
                    }
                }
            } else if (this.type == DashboardType.DATE_NEXT_ORDER_ARRIVES
                    || this.type == DashboardType.MONEY_SPENT_LAST_30_DAYS) {
                bllOrders = new Orders();
                if (bllOrders.load()) {
                    ArrayList<AnOrder> aList = bllOrders.getList();
                    switch (this.type) {
                        case DATE_NEXT_ORDER_ARRIVES:
                            getDateNextArrives(aList);
                            this.description = "The next order to arrive will be on ";
                            break;
                        case MONEY_SPENT_LAST_30_DAYS:
                            countMoney(aList);
                            this.description = "In last 30 days, you have spent $";
                            break;
                        default:
                            break;
                    }
                }
            }
            isSuccessful = true;
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
        }
        return isSuccessful;
    }

    private void getNumOfItemsOutOfStock(ArrayList<AnInventoryItem> aList) {
        int counter = 0;
        for (AnInventoryItem item : aList) {
            if (item.getQuantity().equals(0)) {
                counter += 1;
            }
        }
        this.count = counter;
    }

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
        OrderItems bllOrderItems = new OrderItems();
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
    public String toString() {
        if (count != null) {
            return "- " + this.count + this.description;
        } else if (this.date != null) {
            return "- " + this.description + this.date.toString();
        } else if (this.money != null) {
            return "- " + this.description + this.money;
        } else {
            return "";
        }
    }
}
