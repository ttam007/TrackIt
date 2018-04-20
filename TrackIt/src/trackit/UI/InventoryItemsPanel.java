package trackit.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Inventory panel.
 *
 * @author Brian Diaz
 */
public class InventoryItemsPanel
        extends JPanel {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    /**
     * The name of the panel.
     */
    public static final String TAB_NAME = "Inventory";
    private static final String[] TABLE_LABELS = new String[]{"Item Name", "Qty", "Unit", "SKU", "Expiration", "Status"};
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final ArrayList<AnInventoryItem> inventoryItems = new ArrayList<>();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    private JTable mainTable;
    private JButton btnCreate, btnEdit, btnRemove, btnCheckInOut;
    private final Object[][] data;
    private JScrollPane sp;
    private boolean disableButtons = false;//use this variable to toggle edit and remove buttons on and off

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Inventory items ui
     */
    public InventoryItemsPanel() {
        data = new Object[20][20];

        initializeComponents();
        refreshItems();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Added solely to prevent serialization and Inspector items related to
     * such.
     *
     * @param stream
     * @throws java.io.IOException
     */
    private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
        throw new java.io.NotSerializableException(getClass().getName());
    }

    /**
     * Added solely to prevent serialization and Inspector items related to
     * such.
     *
     * @param stream
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     */
    private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException {
        throw new java.io.NotSerializableException(getClass().getName());
    }

    private void initializeComponents() {
        BorderLayout border = new BorderLayout();
        this.setLayout(border);
        createUIComponents();
        this.setSize(new Dimension(1100, 700));
        this.display();
    }

    /**
     * Toggles whether buttons will be enabled or not.
     */
    private void toggleDisableButton() {

        btnEdit.setEnabled(disableButtons);
        btnRemove.setEnabled(disableButtons);
    }

    private void setButtons() {

        btnCreate = new JButton("Create");
        btnCreate.addActionListener((ActionEvent e) -> {
            InventoryItemDetailsDialog dlgCreate = new InventoryItemDetailsDialog(true, null);
            dlgCreate.display();
        });

        btnEdit = new JButton("Edit");
        btnEdit.setEnabled(disableButtons);

        btnEdit.addActionListener((ActionEvent e) -> {
            //If list item selected then edit item else select item.
            int selectedRow = this.mainTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Select item to edit");
            } else {
                AnInventoryItem anInventoryItem = new AnInventoryItem();
                //TODO: Set anInventoryItem to the value of selectedRow.
                InventoryItemDetailsDialog dlgEdit = new InventoryItemDetailsDialog(false, anInventoryItem);
                dlgEdit.display();
            }
        });

        btnRemove = new JButton("Remove");
        btnRemove.setEnabled(disableButtons);
        btnRemove.addActionListener((ActionEvent e) -> {
            int selectedRow = this.mainTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Select item to remove");
            } else {
                //TODO: remove item from db
                JOptionPane.showMessageDialog(null, "Item removed");
            }

            //TODO: surround below in a for loop
            /*
            if (bll.remove()) {
                //TODO:  close window and return to prior window.
            } else {
                //TODO:  display bll.getErrorMessage() and stay on this window.
            }
             */
        });

        btnCheckInOut = new JButton("Check In/Out");
        btnCheckInOut.addActionListener((ActionEvent e) -> {
            CheckInOutDialog checkIn = new CheckInOutDialog();
            checkIn.display();
        });
    }

    private void createUIComponents() {

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

        mainTable = new JTable(data, TABLE_LABELS);
        // Add action listener to JTable
        mainTable.getSelectionModel().addListSelectionListener((e) -> {
            //if the row is bigger than -1 than we need to enable the buttons
            if (mainTable.getSelectedRow() > -1) {
                disableButtons = true;
                toggleDisableButton();
            }
        });
        mainTable.setBounds(30, 40, 200, 200);
        setButtons();
        sp = new JScrollPane(mainTable);

        add(sp, BorderLayout.CENTER);

        JPanel buttonHolder = new JPanel(new GridLayout(0, 8, 2, 0));

        buttonHolder.add(btnCreate);
        buttonHolder.add(btnEdit);
        buttonHolder.add(btnRemove);
        buttonHolder.add(btnCheckInOut);
        add(buttonHolder, BorderLayout.PAGE_END);
    }

    /**
     * Refreshes the list of items that are displayed in the grid.
     */
    private void refreshItems() {
        this.inventoryItems.clear();

        //TODO:  load items from database.
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
