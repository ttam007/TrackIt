/**
 * @author      Brian Diaz
 * @date 04/10/2018
 * @description handles the creation of the Inventory Item Screen
 *
 */
package trackit.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;
import trackit.DAL.AnInventoryItem;
import trackit.DAL.AnItem;

/**
 * UI Layer: Handles all aspects of the Inventory panel.
 */
public class InventoryItemsUI
        extends JPanel {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    public static final String TAB_NAME = "Inventory";
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final ArrayList<AnInventoryItem> inventoryItems = new ArrayList<>();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    private JTable mainTable;
    private InventoryItemDetailsUI itemCreate, itemEdit;
    private final ArrayList<String> tableHeaders = new ArrayList<>(Arrays.asList("Item Name", "Qty", "Unit", "SKU", "Expiration", "Status"));
    private JButton btnCreate, btnEdit, btnRemove, btnCheckInOut;
    private final Object[][] data;
    private JScrollPane sp;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Inventory items ui
     */
    public InventoryItemsUI() {
        data = new Object[20][20];

        initializeComponents();
        refreshItems();
    }

    private void initializeComponents() {
        BorderLayout border = new BorderLayout();
        this.setLayout(border);
        createUIComponents();
        this.setSize(new Dimension(1100, 700));
        this.display();
    }

    private void setButtons() {

        btnCreate = new JButton("Create");
        btnCreate.addActionListener((ActionEvent e) -> {
            InventoryItemDetailsUI iidCreate = new InventoryItemDetailsUI(true);
            iidCreate.display();
        });

        btnEdit = new JButton("Edit");
        btnEdit.addActionListener((ActionEvent e) -> {
            InventoryItemDetailsUI iidEdit = new InventoryItemDetailsUI(false);
            iidEdit.display();
        });

        btnRemove = new JButton("Remove");
        btnRemove.addActionListener((ActionEvent e) -> System.out.println("REMOVE TEST"));

        btnCheckInOut = new JButton("Check In/Out");
        btnCheckInOut.addActionListener((ActionEvent e) -> {
            CheckInOutUI checkIn = new CheckInOutUI();
            checkIn.display();
        });
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

        buttonHolder.add(btnCreate);
        buttonHolder.add(btnEdit);
        buttonHolder.add(btnRemove);
        buttonHolder.add(btnCheckInOut);
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
        setVisible(true);
    }

    // </editor-fold>
}
