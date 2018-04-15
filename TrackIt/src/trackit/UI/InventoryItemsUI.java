    /**
 * @author      Brian Diaz
 * @date 04/10/2018
 * @description handles the creation of the Inventory Item Screen
 *
 */
package trackit.UI;

import trackit.DAL.AnInventoryItem;
import trackit.DAL.AnItem;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * UI Layer: Handles all aspects of the Inventory panel.
 */
public class InventoryItemsUI extends JPanel {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    private static final String WINDOW_NAME = "Inventory";
    private final ArrayList<AnInventoryItem> inventoryItems;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    private JTable mainTable;
    private InventoryItemDetailsUI itemCreate, itemEdit;
    private final List<String> tableHeaders = new ArrayList<>(Arrays.asList("Item Name", "Qty", "Unit", "SKU", "Expiration", "Status"));
    private JButton create, edit, remove, check;
    private final Object[][] data;
    private JScrollPane sp;
    private JFrame mainFrame;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Invetory items ui
     */
    public InventoryItemsUI() {
        this.inventoryItems = new ArrayList<>();
        this.setLayout(new BorderLayout());
        //mainFrame = new JFrame();
        data = new Object[20][20];
        BorderLayout border = new BorderLayout();
        this.setLayout(border);
        createUIComponents();
        //mainFrame.add(this);
        // mainFrame.pack();
        this.setSize(new Dimension(1100, 700));
        this.display();
        refreshItems();
    }

    /**
     *
     * @return
     */
    

    private void setButtons() {

        create = new JButton("Create");
        //itemCreate = new InventoryItemDetailsUI(true);
        create.addActionListener((event) -> new InventoryItemDetailsUI(true));

        edit = new JButton("Edit");
        //itemEdit = new InventoryItemDetails(false);
        edit.addActionListener((event) -> new InventoryItemDetailsUI(false));

        remove = new JButton("Remove");
        remove.addActionListener((event) -> System.out.println("REMOVE TEST"));

        check = new JButton("Check In/Out");
        check.addActionListener((event) -> new CheckInOutUI());

    }

    private void createUIComponents() {
        setButtons();
        data[0][0] = "Gauze";
        data[0][1] = "3.0";
        data[0][2] = "oz";
        data[0][3] = "231441414";
        data[0][4] = "04-27-2018";
        data[0][5] = "Expired";
        data[1][0] = "Gauze";
        data[1][1] = "3.0";
        data[1][2] = "oz";
        data[1][3] = "231441414";
        data[1][4] = "04-27-2018";
        data[1][5] = "Expired";

        mainTable = new JTable(data, tableHeaders.toArray());
        mainTable.setBounds(30, 40, 200, 200);

        sp = new JScrollPane(mainTable);

        add(sp, BorderLayout.CENTER);

        JPanel buttonHolder = new JPanel(new GridLayout(0, 8, 2, 0));

        buttonHolder.add(create);
        buttonHolder.add(edit);
        buttonHolder.add(remove);
        buttonHolder.add(check);
        add(buttonHolder, BorderLayout.PAGE_END);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

    /**
     * Refreshes the list of items that are displayed in the grid.
     */
    private void refreshItems() {
        this.inventoryItems.clear();

        //TODO:  load items from database.
    }

    /**
     * Launches the AnItem Detail window.
     *
     * @param anItem The item to be shown.
     */
    private void showItemDetails(AnItem anItem) {
        //bll.showDialog(anItem);    
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    /**
     * Displays the frame.
     */
    public void display() {
        System.out.println(String.format("Displaying {0}...", WINDOW_NAME));
        setVisible(true);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        InventoryItemsUI inventoryItemsUI = new InventoryItemsUI();

    }

    // </editor-fold>
}
