package trackit;

/**
 * Used with objects that have multiple Item objects.
 */
public interface IItemHandler {

    //private final ArrayList<Item> items = new ArrayList<>();
    /**
     * Adds an item to the list, or increases quantity by one if already added.
     *
     * @param anItem The item to be added.
     */
    public void addItem(Item anItem);

    /**
     * Removes an item from the list.
     *
     * @param anItem The item to remove.
     */
    public void removeItem(Item anItem);

    /**
     * Reduce the specified quantity of the specified item.
     *
     * @param anItem The item to reduce the quantity of.
     * @param quantity The amount to reduce the item count by.
     * @throws NegativeAmountException If quantity parameter > item's current
     * quantity.
     */
    public void reduceItem(Item anItem, Integer quantity)
            throws NegativeAmountException;

}
