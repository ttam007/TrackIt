package trackit;

import java.util.*;

/**
 * Handles the inventory features.
 */
public class Inventory {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private ArrayList<Item> items = new ArrayList<>();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public Inventory() {

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Adds an item, or updates quantity if already in inventory.
     *
     * @param anItem
     */
    public void addItem(Item anItem) {

    }

    /**
     * Remove the specified quantity of the specified item.
     *
     * @param anItem The item to remove.  If @quantity = 0, then remove all.
     * @param quantity The amount to remove.  0 = all; > 0 that specific amount.
     * @throws NegativeAmountException If quantity > 0 and > current item's quantity.
     */
    public void removeItem(Item anItem, int quantity)
            throws NegativeAmountException {

    }
    
    public ArrayList<Item> getExpiredItems(){
        ArrayList<Item> returnValue = new ArrayList<>();
        
        return returnValue;
    }
    // </editor-fold>
}
