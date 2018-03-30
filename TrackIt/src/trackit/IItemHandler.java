package trackit;

public interface IItemHandler {

    public void addItem(Item anItem);

    public void removeItem(Item anItem);

    public void reduceItem(Item anItem, Integer quantity)
            throws NegativeAmountException;
}
