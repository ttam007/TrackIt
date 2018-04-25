package trackit;

import java.time.LocalDate;
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

    /**
     * refresh
     */
    public void refresh() {
    }


    public boolean getData(){

        boolean isSuccessful =false;
        int indexOfType = this.type.getValue();
        System.out.println(indexOfType);
        if(indexOfType==0 || indexOfType==1){
            bllInventory = new Inventory();
            if(bllInventory.load()){
                ArrayList<AnInventoryItem> aList = bllInventory.getList();
                if(indexOfType==0){

                    getNumOfItemsOutOfStock(aList);
                    this.description = " item(s) out of stock";

                }
                else if(indexOfType ==1){

                    getDateNextExpires(aList);
                    this.description = "The next item to expire will be on ";

                }

                isSuccessful = true;
            }
        }
        else{
            bllOrders  = new Orders();
            if(bllOrders.load()){
                ArrayList<AnOrder> aList = bllOrders.getList();
                if(indexOfType==2){
                    getDateNextArrives(aList);
                    this.description ="The next order to arrive will be on ";

                }
                else if(indexOfType==3){
                    OrderItems bllOrderItem = new OrderItems();
                    countMoney(aList,bllOrderItem.getList());
                    this.description="In 30 days, you have spent $";
                }


                isSuccessful =true;
            }
        }

        return isSuccessful;
    }

    private void getNumOfItemsOutOfStock(ArrayList<AnInventoryItem> aList){
        int counter = 0;
        for(AnInventoryItem item : aList){
            if(item.getQuantity()==0){

               counter+=1;
            }
        }
        this.count=counter;


    }
    private void getDateNextExpires(ArrayList<AnInventoryItem> aList){

        if(aList.size() >0){

            Date min= aList.get(0).getExpirationDate();

            for(AnInventoryItem item : aList){
                Date dateToCompare = item.getExpirationDate();
                if(min.compareTo(dateToCompare)>0){

                    min = dateToCompare;
                }
            }
            this.date = min;
        }

    }
    private void getDateNextArrives(ArrayList<AnOrder> aList){
        if(aList.size() >0){
            Date min = aList.get(0).getDateExpected();
            for(AnOrder item : aList){
                Date dateToCompare = item.getDateExpected();
                if(min.compareTo(dateToCompare)>0){
                    min = dateToCompare;
                }
            }
            this.date = min;
        }


    }
    private Double searchOrderItem(Integer orderPrimaryKey,ArrayList<AnOrderItem> orderItemList){
        Double moneyCount =0.00;
        for(AnOrderItem orderItem : orderItemList){
            if(orderItem.getOrderId()==orderPrimaryKey){
                moneyCount+=orderItem.getExtendedPrice();
            }
        }
        return moneyCount;

    }

    private void countMoney(ArrayList<AnOrder> aList, ArrayList<AnOrderItem> orderItemList){


       if(aList.size()>0){

           Double moneyCount=0.00;
           Date currentDate = new Date();

           Date minus30 = new Date(currentDate.getTime() - 30 *24 * 3600 * 1000l );

           Calendar minus30Cal = Calendar.getInstance();
           minus30Cal.setTime(minus30);

           int minus30DateYear = minus30Cal.get(Calendar.YEAR);
           int minus30DateMonth = minus30Cal.get(Calendar.MONTH);
           int minus30DateDay = minus30Cal.get(Calendar.DAY_OF_MONTH);


           for(AnOrder order : aList){
                Date orderDate = new Date(order.getDateOrdered().getTime());
                Calendar cal = Calendar.getInstance();
                cal.setTime(orderDate);
                int orderDateYear = cal.get(Calendar.YEAR);
                int orderDateMonth = cal.get(Calendar.MONTH);
                int orderDateDay = cal.get(Calendar.DAY_OF_MONTH);

                if(LocalDate.of(orderDateYear,orderDateMonth,orderDateDay).isAfter(LocalDate.of(minus30DateYear,minus30DateMonth,minus30DateDay))){
                    order.getPrimaryKey();

                    moneyCount+=searchOrderItem(order.getPrimaryKey(),orderItemList);
                }
            }
           this.money= moneyCount;
        }



    }

    @Override
    public String toString() {
        if(count!= null){
            return "- "+this.count + this.description;
        }
        else if(this.date!=null){
            return "- "+this.description + this.date.toString();
        }
        else if(this.money!=null){
            return "- "+this.description+ this.money;
        }
        else{
            return "";
        }
    }
}
