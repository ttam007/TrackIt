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

    private String title;
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

    public boolean getData() {
        boolean isSuccessful = false;
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
                isSuccessful = true;
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
                        OrderItems bllOrderItem = new OrderItems();
                        countMoney(aList, bllOrderItem.getList());
                        this.description = "In 30 days, you have spent $";
                        break;
                    default:
                        break;
                }
                isSuccessful = true;
            }
        }

        return isSuccessful;
    }

    private void getNumOfItemsOutOfStock(ArrayList<AnInventoryItem> aList) {
        int counter = 0;
        for (AnInventoryItem item : aList) {
            if (item.getQuantity() == 0) {
                counter += 1;
            }
        }
        this.count = counter;
    }

    private void getDateNextExpires(ArrayList<AnInventoryItem> aList) {
        Date min = new Date(0);
        if (aList != null) {
            for (AnInventoryItem item : aList) {
                Date dateToCompare = item.getExpirationDate();
                if (dateToCompare != null
                        && min.before(dateToCompare)) {
                    min = dateToCompare;
                }
            }
        }
        this.date = (min.getTime() == 0 ? null : min);
    }

    private void getDateNextArrives(ArrayList<AnOrder> aList) {
        Date min = new Date(0);
        if (aList != null) {
            for (AnOrder item : aList) {
                Date dateToCompare = item.getDateExpected();
                if (dateToCompare != null
                        && min.before(dateToCompare)) {
                    min = dateToCompare;
                }
            }
        }
        this.date = (min.getTime() == 0 ? null : min);
    }

    /**

     *
     * @return bllInventory
     */
    public boolean getData() {
        boolean isSuccessful = false;
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
                isSuccessful = true;
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
                        OrderItems bllOrderItem = new OrderItems();
                        countMoney(aList, bllOrderItem.getList());
                        this.description = "In 30 days, you have spent $";
                        break;
                    default:
                        break;
                }
                isSuccessful = true;
            }
        }

        return isSuccessful;
    }

    private void getNumOfItemsOutOfStock(ArrayList<AnInventoryItem> aList) {
        int counter = 0;
        for (AnInventoryItem item : aList) {
            if (item.getQuantity() == 0) {
                counter += 1;
            }
        }
        this.count = counter;
    }

    private void getDateNextExpires(ArrayList<AnInventoryItem> aList) {
        Date min = new Date(0);
        if (aList != null) {
            for (AnInventoryItem item : aList) {
                Date dateToCompare = item.getExpirationDate();
                if (dateToCompare != null
                        && min.before(dateToCompare)) {
                    min = dateToCompare;
                }
            }
        }
        this.date = (min.getTime() == 0 ? null : min);
    }

    private void getDateNextArrives(ArrayList<AnOrder> aList) {
        Date min = new Date(0);
        if (aList != null) {
            for (AnOrder item : aList) {
                Date dateToCompare = item.getDateExpected();
                if (dateToCompare != null
                        && min.before(dateToCompare)) {
                    min = dateToCompare;
                }
            }
        }
        this.date = (min.getTime() == 0 ? null : min);
    }

    /**
     * Gets the total extended price of the specified order.
     *
     * @param orderPrimaryKey The primary key of the order to be summed.
     * @param orderItemList The complete list of order items in the database.
     * @return The sum of the extended price of all items in the order.
     */

     * Gets the total extended price of the specified order.
     *
     * @param orderPrimaryKey The primary key of the order to be summed.
     * @param orderItemList The complete list of order items in the database.
     * @return The sum of the extended price of all items in the order.
     */

    private Double searchOrderItem(Integer orderPrimaryKey, ArrayList<AnOrderItem> orderItemList) {
        Double moneyCount = 0.00;
        for (AnOrderItem orderItem : orderItemList) {
            if (orderItem.getOrderId().equals(orderPrimaryKey)) {
                moneyCount += orderItem.getExtendedPrice();
            }
        }
        return moneyCount;

    }

    private void countMoney(ArrayList<AnOrder> aList, ArrayList<AnOrderItem> orderItemList) {
        Double moneyCount = 0.00;

        if (aList.size() > 0) {
            Calendar minus30Cal = Calendar.getInstance();
            minus30Cal.add(Calendar.DAY_OF_MONTH, -30);

            for (AnOrder order : aList) {
                Date orderDate = order.getDateOrdered();
                if (orderDate != null && orderDate.after(minus30Cal.getTime())) {
                    moneyCount += searchOrderItem(order.getPrimaryKey(), orderItemList);
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
