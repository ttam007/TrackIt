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
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final ArrayList<AnOrderItem> orderItems = new ArrayList<>();
    private final AnOrder bll = new AnOrder();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    private JButton btnCheckIn, btnCheckInAll, btnCreate, btnEdit, btnRemove, btnOK, btnAddItem, btnCancel;
    private JPanel pnlTop, pnlCenter, pnlBtm, pnlBtmLeft, pnlBtmRight;
    private JLabel lblOrderNumber, lblSupplier, lblStatus, lblOrderDate, lblExpectedDate, lblBlank;
    private JTextField tfOrderNumber, tfSupplier, tfStatus, tfOrderDate, tfExpectedDate, tfBlank;
    private JTable mainTable;
    private Date orderDate, expectedDate, sqlOrderDate, sqlExpectedDate;
    
    UtilDateModel orderModel = new UtilDateModel();
    UtilDateModel expectedModel = new UtilDateModel();
    JDatePanelImpl orderDatePanel, expectedDatePanel;
    JDatePickerImpl orderDatePicker, expectedDatePicker;
    
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * order item window
     */
    public OrderItemsFrame() {
        initializeComponents();
        getValues();
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
        orderDatePanel = new JDatePanelImpl(orderModel,p);
        expectedDatePanel = new JDatePanelImpl(expectedModel,p);
         
        //Add all components here and set properties.
        setLayout(new BorderLayout());

        Box topBox, topInnerBx, btmInnerBx, middleBox, bottomBox, combine;

        topBox = Box.createVerticalBox();

        topInnerBx = Box.createHorizontalBox();
        lblOrderNumber = new JLabel("Order Number:");
        topInnerBx.add(lblOrderNumber);
        tfOrderNumber = new JTextField(20);
        topInnerBx.add(tfOrderNumber);

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

        pnlBtm = new JPanel();

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
            orderDate = (Date) orderDatePicker.getModel().getValue();
            sqlOrderDate = Utilities.convertToSQLDate(orderDate);
            expectedDate = (Date) expectedDatePicker.getModel().getValue();
            sqlExpectedDate = Utilities.convertToSQLDate(expectedDate);
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
    private void saveAction() {
        JOptionPane.showMessageDialog(null, "Successfully Updated");
        //TODO:  implement save.
        /*if (successfullySaved) {
                this.dispose();
            } else {
               //TODO:  catch errors and display them.  Do not exit dialog if an error occurs.
            }*/
        this.dispose();
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

    private void getValues() {
        //TODO:  if we need this?
        /*if (bll.load()) {
            this.orderItems.addAll(bll.getItems());
        }*/
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    /**
     * Displays the frame.
     */
    public void display() {
        System.out.println(String.format("Displaying %s...", WINDOW_NAME));
        setVisible(true);
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
