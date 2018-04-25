package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Order panel.
 *
 * @author Douglas, Steven, Diaz
 */
public class OrdersPanel
        extends JPanel {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    /**
     * The name of the panel.
     */
    public static final String TAB_NAME = "Orders";
    private static final String[] TABLE_LABELS = {"Description", "Supplier", "Status", "Order Date", "Expected Date"};

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    /**
     * Integer key = The row in the grid of the AnOrder object.
     */
    private final HashMap<Integer, AnOrder> orders = new HashMap<>();
    private final Orders bllOrders = new Orders();
    /**
     * Integer key = The ASupplier's primary key.
     */
    private final HashMap<Integer, ASupplier> suppliers = new HashMap<>();
    private final Suppliers bllSuppliers = new Suppliers();

    /**
     * Used to toggle edit and remove buttons on and off.
     */
    private boolean makeButtonsEnabled = false;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    private JButton btnCreate, btnRemove, btnEdit;
    private JTable mainTable;
    private DefaultTableModel mainTableModel;
    private JScrollPane sp;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default Constructor.
     */
    public OrdersPanel() {
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
        setLayout(new BorderLayout());

        //add data to suppliers arraylist
        mainTableModel = new DefaultTableModel(TABLE_LABELS, 0);
        mainTable = new JTable(mainTableModel);
        mainTable.setDefaultEditor(Object.class, null);
        mainTable.getTableHeader().setReorderingAllowed(false);
        // Add action listener to JTable
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

        sp = new JScrollPane(mainTable);

        add(sp, BorderLayout.CENTER);

        add(sp, BorderLayout.CENTER);

        JPanel btmSup = new JPanel(new GridLayout(0, 8, 2, 0));

        btnCreate = new JButton(Utilities.BUTTON_CREATE);
        btnCreate.addActionListener((ActionEvent e) -> {
            OrderItemsFrame dlgCreate = new OrderItemsFrame(true, null);
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
                AnOrder anOrder = this.orders.get(selectedRow);
                if (this.bllOrders.remove(anOrder)) {
                    this.refreshGrid();
                    //JOptionPane.showMessageDialog(null, String.format("%s has been removed.", anOrder.getDescription()));
                } else {
                    JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                            Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btmSup.add(btnCreate);
        btmSup.add(btnEdit);
        btmSup.add(btnRemove);

        add(btmSup, BorderLayout.SOUTH);

    }

    private void toggleDisableButton() {
        btnEdit.setEnabled(makeButtonsEnabled);
        btnRemove.setEnabled(makeButtonsEnabled);
    }

    private void initTableData(ArrayList<AnOrder> listOrders) {
        if (this.orders != null) {
            int counter = 0;
            for (AnOrder anOrder : listOrders) {
                //{"Description", "Supplier", "Status", "Order Date", "Expected Date"};
                Object[] data = {anOrder.getDescription(),
                    this.suppliers.get(anOrder.getOrderedFrom()).getNickname(),
                    anOrder.getDateOrdered(), anOrder.getOrderStatus(),
                    anOrder.getDateExpected()};
                mainTableModel.addRow(data);
                this.orders.put(counter, anOrder);
                counter++;
            }
        }
    }

    /**
     * Refreshes the grid with current data from the database.
     */
    public final void refreshGrid() {
        //Clear the ArrayList and JTable, which should be done backwards.
        this.orders.clear();
        for (int i = mainTableModel.getRowCount() - 1; i >= 0; i--) {
            mainTableModel.removeRow(i);
        }

        //Now load fresh data from database.
        if (this.bllSuppliers.load()) {
            ArrayList<ASupplier> listSuppliers = this.bllSuppliers.getList();
            listSuppliers.forEach((aSupplier) -> {
                this.suppliers.put(aSupplier.getPrimaryKey(), aSupplier);
            });
        } else {
            JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        }

        if (this.bllOrders.load()) {
            ArrayList<AnOrder> listOrders = this.bllOrders.getList();
            initTableData(listOrders);
        } else {
            JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Pops the detail item dialog if an item is selected.
     */
    private void editAction() {
        int selectedRow = mainTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Select item to edit");
        } else {
            AnOrder anOrder = this.orders.get(selectedRow);
            OrderItemsFrame dlgEdit = new OrderItemsFrame(false, anOrder);
            dlgEdit.setLocationRelativeTo(this);
            if (dlgEdit.display() == DialogResultType.OK) {
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
    // <editor-fold defaultstate="collapsed" desc="SubClasses">

    // </editor-fold>
}
