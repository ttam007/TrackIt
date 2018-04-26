package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the AnOrder Details dialog. This is a
 * combination of the Edit AnOrder Details and the OrderItems grid.
 *
 * @author Douglas, Bond, Steven
 */
public class OrderItemsFrame
        extends JDialog {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    /**
     * The name of the window.
     */
    public static final String WINDOW_NAME = "Order Details";
    private static final String[] TABLE_LABELS = {"Item Name", "Unit", "SKU", "Quantity", "Price", "Ext Price"};
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    //For editing the Order.
    private final boolean isCreateMode;
    private final AnOrder anOrder;
    private DialogResultType dialogResult = DialogResultType.NONE;
    private final Orders bllOrders = new Orders();
    private final Suppliers bllSuppliers = new Suppliers();

    //For the grid.
    private final HashMap<Integer, AnOrderItem> orderItems = new HashMap<>();
    private final OrderItems bllOrderItems = new OrderItems();

    /**
     * Used to toggle edit and remove buttons on and off.
     */
    private boolean makeButtonsEnabled = false;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    private JButton btnCheckIn, btnCheckInAll, btnCreate, btnEdit, btnRemove, btnOK, btnAddItem, btnCancel;
    private JPanel pnlTop, pnlCenter, pnlBtm, pnlBtmLeft, pnlBtmRight;
    private JLabel lblDescription, lblSupplier, lblStatus, lblOrderDate, lblExpectedDate;
    private JTextField tfDescription;
    private JDatePickerImpl orderDatePicker, expectedDatePicker;
    private JScrollPane scrollPane;
    private JComboBox<OrderStatusType> cboOrderStatus;
    private JComboBox<ASupplier> cboSuppliers;
    private JTable mainTable;
    private DefaultTableModel mainTableModel;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates an instance of this frame.
     *
     * @param useCreateMode True = an Order will be created; False = an existing
     * Order will be edited.
     * @param anOrder The Order to be edited.
     */
    public OrderItemsFrame(boolean useCreateMode, AnOrder anOrder) {
        this.isCreateMode = useCreateMode;
        if (this.isCreateMode) {
            this.anOrder = new AnOrder();
        } else if (anOrder == null) {
            throw new IllegalArgumentException("When 'useCreateMode' = false, then a non-null anOrder must be provided.");
        } else {
            this.anOrder = anOrder;
        }

        initializeComponents();
        populateComponents();
        refreshGrid(true);
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

    /**
     * Sets up all components used in this frame.
     */
    private void initializeComponents() {
        //Setup main frame
        int frameWidth = 1028;
        int frameHeight = 700;
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        setPreferredSize(dimFrame);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new CloseQuery());
        this.getRootPane().setDefaultButton(btnOK);
        this.setModal(true);

        //Add all components here and set properties.
        setLayout(new BorderLayout());

        Box topBox, topInnerBx, btmInnerBx, middleBox, bottomBox, combine;

        topBox = Box.createVerticalBox();
        JPanel pnlTopBpx = new JPanel();
        pnlTopBpx.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 2, 5, 0);
        gbc.anchor = GridBagConstraints.LINE_START;

        //topInnerBx = Box.createHorizontalBox();
        lblDescription = new JLabel("Order Description:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlTopBpx.add(lblDescription, gbc);
        //topInnerBx.add(lblDescription);
        tfDescription = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        pnlTopBpx.add(tfDescription, gbc);
        //topInnerBx.add(tfDescription);

        lblSupplier = new JLabel("Supplier:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        pnlTopBpx.add(lblSupplier, gbc);
        //topInnerBx.add(lblSupplier);
        cboSuppliers = new JComboBox<>(getSupplierList());
        gbc.gridx = 3;
        gbc.gridy = 0;
        pnlTopBpx.add(cboSuppliers, gbc);
        //pnlTopBpx.add(tfSupplier);
        cboSuppliers.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                getSupplierList();
                validate();
            }
        });

        lblOrderDate = new JLabel("Order Date:");
        gbc.gridx = 4;
        gbc.gridy = 0;
        pnlTopBpx.add(lblOrderDate, gbc);

        orderDatePicker = Utilities.getDatePicker();
        gbc.gridx = 5;
        gbc.gridy = 0;
        pnlTopBpx.add(orderDatePicker, gbc);

        lblStatus = new JLabel("Status:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlTopBpx.add(lblStatus, gbc);

        cboOrderStatus = new JComboBox<>(OrderStatusType.values());
        gbc.gridx = 1;
        gbc.gridy = 1;
        pnlTopBpx.add(cboOrderStatus, gbc);

        lblExpectedDate = new JLabel("Expected Date:");
        gbc.gridx = 4;
        gbc.gridy = 1;
        pnlTopBpx.add(lblExpectedDate, gbc);

        expectedDatePicker = Utilities.getDatePicker();
        gbc.gridx = 5;
        gbc.gridy = 1;
        pnlTopBpx.add(expectedDatePicker, gbc);
        expectedDatePicker.getModel().addPropertyChangeListener((e) -> {
            if (e.getPropertyName().equals("value")) {
                expectedDateLeaveAction((java.util.Date) e.getOldValue());
            }
        });

        btnCheckIn = new JButton(Utilities.BUTTON_CHECKIN);
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlTopBpx.add(btnCheckIn, gbc);

        btnCheckIn.addActionListener((ActionEvent e) -> {
            checkInAction();
        });

        btnCheckInAll = new JButton(Utilities.BUTTON_CHECKINALL);
        gbc.gridx = 1;
        gbc.gridy = GridBagConstraints.RELATIVE;
        pnlTopBpx.add(btnCheckInAll, gbc);

        btnCheckInAll.addActionListener((ActionEvent e) -> {
            //TODO:  Call into BLL for check-in.
            JOptionPane.showMessageDialog(this, "All Items Checked In");
        });

        topBox.add(pnlTopBpx);
        add(topBox, BorderLayout.NORTH);

        bottomBox = Box.createHorizontalBox();

        //add data to suppliers arraylist 
        mainTableModel = new DefaultTableModel(TABLE_LABELS, 0);
        mainTable = new JTable(mainTableModel);
        scrollPane = new JScrollPane(mainTable);
        mainTable.setFillsViewportHeight(true);
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

        add(scrollPane, BorderLayout.CENTER);
        bottomBox.add(scrollPane);

        combine = Box.createVerticalBox();
        combine.add(bottomBox);
        add(combine, BorderLayout.CENTER);

        pnlBtm = new JPanel(new GridLayout(0, 8, 2, 0));
        btnAddItem = new JButton(Utilities.BUTTON_ADD);
        pnlBtm.add(btnAddItem);
        btnAddItem.addActionListener((ActionEvent e) -> {
            addAction();
        });

        btnCreate = new JButton(Utilities.BUTTON_CREATE);
        pnlBtm.add(btnCreate);
        btnCreate.addActionListener((ActionEvent e) -> {
            createAction();
        });

        btnEdit = new JButton(Utilities.BUTTON_EDIT);
        pnlBtm.add(btnEdit);
        btnEdit.addActionListener((ActionEvent e) -> {
            editAction();
        });

        btnRemove = new JButton(Utilities.BUTTON_REMOVE);
        pnlBtm.add(btnRemove);
        btnRemove.addActionListener((ActionEvent e) -> {
            removeAction();
        });

        btnOK = new JButton(Utilities.BUTTON_OK);
        pnlBtm.add(btnOK);
        btnOK.addActionListener((ActionEvent e) -> {
            saveAction();
        });

        btnCancel = new JButton(Utilities.BUTTON_CANCEL);
        pnlBtm.add(btnCancel);
        btnCancel.addActionListener((ActionEvent e) -> {
            cancelAction();
        });

        add(pnlBtm, BorderLayout.SOUTH);

        //Finalizations
        pack();
    }

    /**
     * Populates all the UI components from the object in memory.
     */
    private void populateComponents() {
        this.tfDescription.setText(this.anOrder.getDescription());
        if (!this.isCreateMode) {
            int key = this.anOrder.getOrderedFrom();
            if (this.bllSuppliers.load(key)) {
                ASupplier aSupplier = this.bllSuppliers.getList().get(0);
                this.cboSuppliers.getModel().setSelectedItem(aSupplier);
                //this.cboSuppliers.
            }
        }
        this.cboOrderStatus.getModel().setSelectedItem(this.anOrder.getOrderStatus());
        Utilities.setDatePickersDate(this.orderDatePicker, this.anOrder.getDateOrdered());
        //TODO:  fix this:  
        //Utilities.setDatePickersDate(this.expectedDatePicker, this.anOrder.getDateExpected());
    }

    /**
     * Populates the object in memory from all the UI components.
     */
    private boolean populateObject() {
        boolean returnValue = false;
        //TODO:  sort this out so boolean return is used instead of try/catch block.
        try {
            this.anOrder.setDescription(this.tfDescription.getText());
            ASupplier aSupplier = (ASupplier) this.cboSuppliers.getModel().getSelectedItem();
            this.anOrder.setOrderedFrom(aSupplier.getPrimaryKey());
            this.anOrder.setOrderStatus((OrderStatusType) this.cboOrderStatus.getModel().getSelectedItem());
            this.anOrder.setDateOrdered((Date) this.orderDatePicker.getModel().getValue());
            this.anOrder.setDateExpected((Date) this.expectedDatePicker.getModel().getValue());
            returnValue = true;
        } catch (java.sql.SQLException | RuntimeException ex) {
            Utilities.setErrorMessage(ex);
            JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        }
        return returnValue;
    }

    private void checkInAction() {
        int selectedRow = this.mainTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select item to check in");
        } else {
            AnOrderItem anOrderItem = this.orderItems.get(selectedRow);
            try {
                AnInventoryItem anInventoryItem = AnInventoryItem.load(anOrderItem.getPrimaryKey());
                int checkQuant = anOrderItem.getQuantityOrdered();
                int oldQuant = anInventoryItem.getQuantity();
                anInventoryItem.setQuantity(checkQuant + oldQuant);
                removeItem(selectedRow);
                JOptionPane.showMessageDialog(this, "Item Checked In");
            } catch (Exception ex) {
                Utilities.setErrorMessage(ex);
                JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                        Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handles the save action. If any errors, then display error message
     * instead.
     */
    private void saveAction() {
        if (populateObject()) {
            if (this.bllOrders.save(this.anOrder, bllOrderItems)) {
                this.dialogResult = DialogResultType.OK;
                this.setVisible(false);
                this.dispose();
            } else {
                this.dialogResult = DialogResultType.CANCEL;
                JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                        Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeItem(int row) {
        AnOrderItem anOrderItem = this.orderItems.get(row);
        if (this.bllOrderItems.remove(anOrderItem)) {
            this.refreshGrid(true);
        } else {
            JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the cancel action. If any errors, then display error message
     * instead.
     *
     */
    private void cancelAction() {
        this.dialogResult = DialogResultType.CANCEL;
        this.setVisible(false);
        this.dispose();
    }

    private void toggleDisableButton() {
        btnEdit.setEnabled(makeButtonsEnabled);
        btnRemove.setEnabled(makeButtonsEnabled);
    }

    private void initTableData(ArrayList<AnOrderItem> aList) {
        if (this.orderItems != null) {
            int counter = 0;
            for (AnOrderItem anOrderItem : aList) {
                //{"Item Name", "Unit", "SKU", "Quantity", "Price", "Ext Price"};
                Object[] data = {anOrderItem.getDescription(), anOrderItem.getSizeUnit(),
                    anOrderItem.getSku(), anOrderItem.getQuantityOrdered(),
                    Utilities.formatAsCurrency(anOrderItem.getPrice()),
                    Utilities.formatAsCurrency(anOrderItem.getExtendedPrice())};
                mainTableModel.addRow(data);
                this.orderItems.put(counter, anOrderItem);
                counter++;
            }
        }
    }

    /**
     * Refreshes the grid with current data from the database.
     *
     * @param willPullFromDatabase True = all data is pulled from the database;
     * False = taken is only pulled from memory.
     */
    private void refreshGrid(boolean willPullFromDatabase) {
        //Clear the ArrayList and JTable, which should be done backwards.
        this.orderItems.clear();
        for (int i = mainTableModel.getRowCount() - 1; i >= 0; i--) {
            mainTableModel.removeRow(i);
        }

        //Now load fresh data
        if (willPullFromDatabase) {
            if (!this.bllOrderItems.loadByOrder(this.anOrder.getPrimaryKey())) {
                JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                        Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
            }
        }
        ArrayList<AnOrderItem> aList = this.bllOrderItems.getList();
        initTableData(aList);
    }

    /**
     * Pops the detail item dialog for adding an item to this order.
     */
    private void addAction() {
        AnOrderItem anOrderItem = new AnOrderItem();
        OrderItemDetailsDialog dlgAdd = new OrderItemDetailsDialog(true, anOrderItem, this.bllOrderItems);
        dlgAdd.setLocationRelativeTo(this);
        if (dlgAdd.display() == DialogResultType.OK) {
            this.refreshGrid(false);
        }
    }

    /**
     * Pops the Inventory Item dialog in create mode.
     */
    private void createAction() {
        InventoryItemDetailsDialog dlgCreate = new InventoryItemDetailsDialog(true, null);
        dlgCreate.setLocationRelativeTo(this);
        if (dlgCreate.display() == DialogResultType.OK) {
            this.refreshGrid(true);
        }
    }

    /**
     * Pops the detail item dialog if an item is selected.
     */
    private void editAction() {
        int selectedRow = this.mainTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select item to remove");
        } else {
            AnOrderItem anOrderItem = this.orderItems.get(selectedRow);
            OrderItemDetailsDialog dlgEdit = new OrderItemDetailsDialog(false, anOrderItem, this.bllOrderItems);
            dlgEdit.setLocationRelativeTo(this);
            if (dlgEdit.display() == DialogResultType.OK) {
                this.refreshGrid(true);
            }
        }
    }

    /**
     * Removes the selected item.
     */
    private void removeAction() {
        int selectedRow = this.mainTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Select item to remove");
        } else {
            AnOrderItem anOrderItem = this.orderItems.get(selectedRow);
            if (this.bllOrderItems.remove(anOrderItem)) {
                this.refreshGrid(true);
                JOptionPane.showMessageDialog(null,
                        String.format("%s has been removed.", anOrderItem.getDescription()));
            } else {
                JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                        Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * When focus is left, compare to the ordered date.
     */
    private void expectedDateLeaveAction(java.util.Date originalExpectedDate) {
        java.util.Date orderedDate = Utilities.getDatePickersDate(orderDatePicker);
        java.util.Date expectedDate = Utilities.getDatePickersDate(expectedDatePicker);
        if (orderedDate != null && expectedDate != null
                && expectedDate.before(orderedDate)) {
            JOptionPane.showMessageDialog(OrderItemsFrame.this,
                    "The expected date can't be prior to the ordered date.",
                    "Date Issue", JOptionPane.INFORMATION_MESSAGE);
            Utilities.setDatePickersDate(expectedDatePicker, originalExpectedDate);
        }
    }

    private ASupplier[] getSupplierList() {
        ASupplier[] arraySuppliers = new ASupplier[]{};
        ArrayList<ASupplier> listSuppliers = new ArrayList<>();

        if (bllSuppliers.load()) {
            listSuppliers = bllSuppliers.getList();
        } else {
            JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        }
        return listSuppliers.toArray(arraySuppliers);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Package Methods">
    void addOrderItem(AnOrderItem anOrderItem) {
        //TODO:  finish this
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Displays the frame.
     *
     * @return The DialogReturnType which tells how the dialog was closed.
     */
    public DialogResultType display() {
        setVisible(true);
        return this.dialogResult;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SubClasses">
    /**
     * Handles all aspects of closing the program.
     */
    private class CloseQuery
            extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            JDialog frame = OrderItemsFrame.this;
            int result = JOptionPane.showConfirmDialog(frame,
                    "Do you want to save?", "Close Query",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                saveAction();
            } else {
                cancelAction();
            }
        }
    }
    // </editor-fold>
}
