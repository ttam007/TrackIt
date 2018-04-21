package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the AnOrder Details dialog. This is a
 * combination of the Edit AnOrder Details and the OrderItems grid.
 *
 * @author Douglas, Bond, Steven
 */
public class OrderItemsFrame
        extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    /**
     * The name of the window.
     */
    public static final String WINDOW_NAME = "Order Details";
    private static final String[] TABLE_LABELS = {"Item Name", "Unit", "SKU", "Quantity", "Price", "Ext Price"};
    private final boolean isCreateMode;
    private final AnOrder anOrder;
    private final Orders bll = new Orders();
    private DialogResultType dialogResult = DialogResultType.NONE;

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    private JButton btnCheckIn, btnCheckInAll, btnCreate, btnEdit, btnRemove, btnOK, btnAddItem, btnCancel;
    private JPanel pnlTop, pnlCenter, pnlBtm, pnlBtmLeft, pnlBtmRight;
    private JLabel lblDescription, lblSupplier, lblStatus, lblOrderDate, lblExpectedDate, lblBlank;
    private JTextField tfDescription, tfSupplier, tfStatus, tfOrderDate, tfExpectedDate, tfBlank;
    private JTable mainTable;
    private Date orderDate, expectedDate, sqlOrderDate, sqlExpectedDate;

    UtilDateModel orderModel = new UtilDateModel();
    UtilDateModel expectedModel = new UtilDateModel();
    JDatePanelImpl orderDatePanel, expectedDatePanel;
    JDatePickerImpl orderDatePicker, expectedDatePicker;

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
            throw new IllegalArgumentException("When 'useCreateMode' = false, then a non-null anInventoryItem must be provided.");
        } else {
            this.anOrder = anOrder;
        }

        initializeComponents();
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
        setVisible(true);
        this.getRootPane().setDefaultButton(btnOK);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        orderDatePanel = new JDatePanelImpl(orderModel, p);
        expectedDatePanel = new JDatePanelImpl(expectedModel, p);

        //Add all components here and set properties.
        setLayout(new BorderLayout());

        Box topBox, topInnerBx, btmInnerBx, middleBox, bottomBox, combine;

        topBox = Box.createVerticalBox();

        topInnerBx = Box.createHorizontalBox();
        lblDescription = new JLabel("Order Description:");
        topInnerBx.add(lblDescription);
        tfDescription = new JTextField(20);
        topInnerBx.add(tfDescription);

        lblSupplier = new JLabel("Supplier:");
        topInnerBx.add(lblSupplier);
        tfSupplier = new JTextField(20);
        topInnerBx.add(tfSupplier);

        lblOrderDate = new JLabel("      Order Date:");
        topInnerBx.add(lblOrderDate);
        orderDatePicker = new JDatePickerImpl(orderDatePanel, new DateLabelFormatter());
        topInnerBx.add(orderDatePicker);

        btmInnerBx = Box.createHorizontalBox();
        lblStatus = new JLabel("            Status:");
        btmInnerBx.add(lblStatus);
        tfStatus = new JTextField(20);
        btmInnerBx.add(tfStatus);

        lblStatus = new JLabel("                          ");
        btmInnerBx.add(lblStatus);

        lblExpectedDate = new JLabel("                                                     Expected Date:");
        btmInnerBx.add(lblExpectedDate);
        expectedDatePicker = new JDatePickerImpl(expectedDatePanel, new DateLabelFormatter());
        btmInnerBx.add(expectedDatePicker);

        topBox.add(topInnerBx);
        topBox.add(btmInnerBx);

        add(topBox, BorderLayout.NORTH);

        middleBox = Box.createHorizontalBox();

        btnCheckIn = new JButton("Check In");
        middleBox.add(btnCheckIn);
        btnCheckIn.addActionListener((ActionEvent e) -> {
            //TODO:  Call into BLL for check-in.
            JOptionPane.showMessageDialog(this, "Item Checked In");
        });

        btnCheckInAll = new JButton("Check In All");
        middleBox.add(btnCheckInAll);
        btnCheckInAll.addActionListener((ActionEvent e) -> {
            //TODO:  Call into BLL for check-in.
            JOptionPane.showMessageDialog(this, "All Items Checked In");
        });

        bottomBox = Box.createHorizontalBox();

        //add data to suppliers arraylist 
        Object[][] testData = {{"paper", "pk", "12-34563487-0", "7", "$12.95", "$276.23"}, {"paper", "pk", "12-34563487-0", "7", "$12.95", "$276.23"}, {"paper", "pk", "12-34563487-0", "7", "$12.95", "$276.23"}};
        mainTable = new JTable(testData, TABLE_LABELS);
        JScrollPane scrollPane = new JScrollPane(mainTable);
        mainTable.setFillsViewportHeight(true);
        mainTable.setDefaultEditor(Object.class, null);

        add(scrollPane, BorderLayout.CENTER);

        bottomBox.add(scrollPane);

        combine = Box.createVerticalBox();
        combine.add(middleBox);
        combine.add(bottomBox);
        add(combine, BorderLayout.CENTER);

        pnlBtm = new JPanel(new GridLayout(0, 8, 2, 0));

        btnAddItem = new JButton("Add Item");
        pnlBtm.add(btnAddItem);
        btnAddItem.addActionListener((ActionEvent e) -> {
            OrderItemDetailsDialog dlgAdd = new OrderItemDetailsDialog(true, null);
            dlgAdd.display();
        });

        btnCreate = new JButton("Create");
        pnlBtm.add(btnCreate);
        btnCreate.addActionListener((ActionEvent e) -> {
            InventoryItemDetailsDialog dlgCreate = new InventoryItemDetailsDialog(true, null);
            dlgCreate.display();
        });

        btnEdit = new JButton("Edit");
        pnlBtm.add(btnEdit);
        btnEdit.addActionListener((ActionEvent e) -> {
            int selectedRow = this.mainTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Select item to remove");
            } else {
                AnOrderItem anOrderItem = new AnOrderItem();
                //TODO: Set anOrderItem to the value of selectedRow.
                OrderItemDetailsDialog dlgEdit = new OrderItemDetailsDialog(false, anOrderItem);
                dlgEdit.display();
            }
        });

        btnRemove = new JButton("Remove");
        pnlBtm.add(btnRemove);
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

        btnOK = new JButton("OK");
        pnlBtm.add(btnOK);
        btnOK.addActionListener((ActionEvent e) -> {
            saveAction();
        });

        btnCancel = new JButton("Cancel");
        pnlBtm.add(btnCancel);
        btnCancel.addActionListener((ActionEvent e) -> {
            cancelAction();
        });

        add(pnlBtm, BorderLayout.SOUTH);

        //Finalizations
        pack();
    }

    /**
     * Handles the save action. If any errors, then display error message
     * instead.
     *
     */
    /*private void populateComponents() {
        this.tfDescription.setText(this.anOrder.getDescription());
        this.tfSupplier.getEditor().setItem(this.anOrder.getOrderedFrom().getText());
        this.OrderDatePicker = sqlOrderDate = Utilities.convertToSQLDate((Date) orderDatePicker.getModel().getValue()); 
    }*/
    private boolean populateObject() {
        boolean returnValue = false;
        //TODO:  sort this out so boolean return is used instead of try/catch block.
        try {
            this.anOrder.setDescription(this.tfDescription.getText());
            this.anOrder.setOrderedFrom(Integer.parseInt(this.tfSupplier.getText()));
            this.anOrder.setDateOrdered(Utilities.convertToSQLDate((Date) this.orderDatePicker.getModel().getValue()));
            this.anOrder.setOrderStatus(this.tfStatus.getText());
            this.anOrder.setDateExpected(Utilities.convertToSQLDate((Date) this.expectedDatePicker.getModel().getValue()));
            returnValue = true;
        } catch (java.sql.SQLException exSQL) {
            JOptionPane.showMessageDialog(this, this.anOrder.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        }
        return returnValue;
    }

    private void saveAction() {
        if (populateObject()) {
            if (this.bll.save(this.anOrder)) {
                this.dialogResult = DialogResultType.OK;
                JOptionPane.showMessageDialog(null, "Successfully Saved.");
                this.setVisible(false);
                this.dispose();
            } else {
                this.dialogResult = DialogResultType.CANCEL;
                JOptionPane.showMessageDialog(this, this.bll.getErrorMessage(),
                        Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handles the cancel action. If any errors, then display error message
     * instead.
     *
     */
    private void cancelAction() {
        JOptionPane.showMessageDialog(null, "Change Cancelled");
        //TODO:  close window and return to prior window.
        this.dispose();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Displays the frame.
     *
     * @return The DialogReturnType which tells how the dialog was closed.
     */
    public DialogResultType display() {
        System.out.println(String.format("Displaying %s...", WINDOW_NAME));
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
            JFrame frame = OrderItemsFrame.this;
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
