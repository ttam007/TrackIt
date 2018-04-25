package trackit.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private final HashMap<Integer, AnInventoryItem> inventoryItems = new HashMap<>();
    private final Inventory bll = new Inventory();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    private JTable mainTable;
    private JButton btnCreate, btnEdit, btnRemove, btnCheckInOut;
    private DefaultTableModel mainTableModel;
    private JScrollPane sp;
    private boolean makeButtonsEnabled = false;//use this variable to toggle edit and remove buttons on and off

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Inventory items ui
     */
    public InventoryItemsPanel() {
        initializeComponents();
        refreshGrid();
        toggleDisableButton();
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
        this.setSize(new Dimension(1100, 700));

        mainTableModel = new DefaultTableModel(TABLE_LABELS, 0);

        mainTable = new JTable(mainTableModel);
        mainTable.getTableHeader().setReorderingAllowed(false);
        mainTable.setDefaultEditor(Object.class, null);
        mainTable.getSelectionModel().addListSelectionListener((e) -> {
            //if the row is bigger than -1 than we need to enable the buttons
            if (mainTable.getSelectedRow() > -1) {
                makeButtonsEnabled = true;
                toggleDisableButton();
            }
        });
        mainTable.addMouseListener(new MouseAdapter() {
            /**
             * https://stackoverflow.com/questions/14852719/double-click-listener-on-jtable-in-java
             *
             * @param mouseEvent
             */
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    editAction();
                }
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
     * Toggles whether buttons will be enabled or not.
     */
    private void toggleDisableButton() {
        btnEdit.setEnabled(makeButtonsEnabled);
        btnRemove.setEnabled(makeButtonsEnabled);
    }

    private void setButtons() {

        btnCreate = new JButton(Utilities.BUTTON_CREATE);
        btnCreate.addActionListener((ActionEvent e) -> {
            InventoryItemDetailsDialog dlgCreate = new InventoryItemDetailsDialog(true, null);
            dlgCreate.setLocationRelativeTo(this);
            if (dlgCreate.display() == DialogResultType.OK) {
                this.refreshGrid();
            }
        });

        btnEdit = new JButton(Utilities.BUTTON_EDIT);
        btnEdit.addActionListener((ActionEvent e) -> {
            editAction();
        });

        btnRemove = new JButton(Utilities.BUTTON_REMOVE);
        btnRemove.addActionListener((ActionEvent e) -> {
            int selectedRow = this.mainTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Select item to remove");
            } else {
                AnInventoryItem anInventoryItem = this.inventoryItems.get(selectedRow);
                if (this.bll.remove(anInventoryItem)) {
                    this.refreshGrid();
                    JOptionPane.showMessageDialog(null,
                            String.format("%s has been removed.", anInventoryItem.getDescription()));
                } else {
                    JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                            Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCheckInOut = new JButton(Utilities.BUTTON_CHECKINOUT);
        btnCheckInOut.addActionListener((ActionEvent e) -> {
            checkInOutAction();
        });
    }

    /**
     * populates table data in a way that is dynamic
     */
    private void initTableData(ArrayList<AnInventoryItem> aList) {
        if (this.inventoryItems != null) {
            int counter = 0;
            for (AnInventoryItem anInventoryItem : aList) {
                Object[] data = {anInventoryItem.getDescription(), anInventoryItem.getQuantity(), anInventoryItem.getSizeUnit(), anInventoryItem.getSku(), anInventoryItem.getExpirationDate(), anInventoryItem.getItemStatus()};
                mainTableModel.addRow(data);
                this.inventoryItems.put(counter, anInventoryItem);
                counter++;
            }
        }
    }

    /**
     * Refreshes the list of items that are displayed in the grid.
     */
    public final void refreshGrid() {
        this.inventoryItems.clear();
        for (int i = mainTableModel.getRowCount() - 1; i >= 0; i--) {
            mainTableModel.removeRow(i);
        }

        if (bll.load()) {
            ArrayList<AnInventoryItem> aList = bll.getList();
            initTableData(aList);
        } else {
            JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Pops the detail item dialog if an item is selected.
     */
    private void editAction() {
        int selectedRow = this.mainTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select item to edit");
        } else {
            AnInventoryItem anInventoryItem = this.inventoryItems.get(selectedRow);
            InventoryItemDetailsDialog dlgEdit = new InventoryItemDetailsDialog(false, anInventoryItem);
            dlgEdit.setLocationRelativeTo(this);
            if (dlgEdit.display() == DialogResultType.OK) {
                this.refreshGrid();
            }
        }
    }

    private void checkInOutAction() {
        int selectedRow = this.mainTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select item to edit");
        } else {
            AnInventoryItem anInventoryItem = this.inventoryItems.get(selectedRow);
            CheckInOutDialog checkIn = new CheckInOutDialog(anInventoryItem);
            checkIn.setLocationRelativeTo(this);
            if (checkIn.display() == DialogResultType.OK) {
                this.refreshGrid();
            }
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Displays the frame.
     */
    public void display() {
        setVisible(true);
    }

    /**
     * Gets the array of table column headers.
     *
     * @return The array of column headers.
     */
    public static String[] getColumnHeaders() {
        return TABLE_LABELS.clone();
    }
    // </editor-fold>
}
