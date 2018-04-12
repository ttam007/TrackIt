package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Order Details dialog. This is a
 * combination of the Edit Order Details and the OrderItems grid.
 *
 * @author Douglas
 */
public class OrderItemsUI
        extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    private static final String WINDOW_NAME = "Order Details";
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final ArrayList<OrderItem> orderItems = new ArrayList<>();
    private final Order bll = new Order();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">

    JButton btnCheckIn, btnCheckInAll, btnCreate, btnEdit, btnRemove, btnOK, btnAddItem, btnCancel;
    JPanel pnlTop, pnlCenter, pnlBtm;
    JLabel lblOrderNumber, lblSupplier, lblStatus, lblOrderDate, lblExpectedDate;
    JTextField tfOrderNumber, tfSupplier, tfStatus, tfOrderDate, tfExpectedDate;
    String[] ordersLabel = {"Item Name", "Unit", "SKU", "Quantity", "Price", "Ext Price"};
    JTable ordersTable;
    OrderItemDetailsUI details;
    int selectedRow;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public OrderItemsUI() {
        initializeComponents();
        getValues();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

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

        //Add all components here and set properties.
        setLayout(new BorderLayout());

        pnlTop = new JPanel();
        //layGroup order-details

        lblOrderNumber = new JLabel("Order Number");
        pnlTop.add(lblOrderNumber);
        tfOrderNumber = new JTextField(20);
        pnlTop.add(tfOrderNumber);

        lblSupplier = new JLabel("Supplier");
        pnlTop.add(lblSupplier);
        tfSupplier = new JTextField(20);
        pnlTop.add(tfSupplier);

        lblOrderDate = new JLabel("Order Date");
        pnlTop.add(lblOrderDate);
        tfOrderDate = new JTextField(20);
        pnlTop.add(tfOrderDate);

        lblStatus = new JLabel("Status");
        pnlTop.add(lblStatus);
        tfStatus = new JTextField(20);
        pnlTop.add(tfStatus);

        lblExpectedDate = new JLabel("Expected Date");
        pnlTop.add(lblExpectedDate);
        tfExpectedDate = new JTextField(20);
        pnlTop.add(tfExpectedDate);

        btnCheckIn = new JButton("Check In");
        pnlTop.add(btnCheckIn);
        btnCheckIn.addActionListener((ActionEvent e) -> {
            //TODO
        });

        btnCheckInAll = new JButton("Check In All");
        pnlTop.add(btnCheckInAll);
        btnCheckInAll.addActionListener((ActionEvent e) -> {
            //TODO
        });

        add(pnlTop, BorderLayout.NORTH);

        //add data to suppliers arraylist 
        Object[][] suppliersTestData = {{"paper", "pk", "12-34563487-0", "7", "$12.95", "276.23"}, {"paper", "pk", "12-34563487-0", "7", "$12.95", "276.23"}, {"paper", "pk", "12-34563487-0", "7", "$12.95", "276.23"}};
        ordersTable = new JTable(suppliersTestData, ordersLabel);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        ordersTable.setFillsViewportHeight(true);
        ordersTable.setDefaultEditor(Object.class, null);

        add(scrollPane, BorderLayout.CENTER);

        pnlBtm = new JPanel();

        btnAddItem = new JButton("Add Item");
        pnlBtm.add(btnAddItem);
        btnAddItem.addActionListener((ActionEvent e) -> {
            //TODO
        });

        btnCreate = new JButton("Create");
        pnlBtm.add(btnCreate);
        btnCreate.addActionListener((ActionEvent e) -> {
            //TODO
        });

        btnEdit = new JButton("Edit");
        pnlBtm.add(btnEdit);
        btnEdit.addActionListener((ActionEvent e) -> {
            //TODO
        });

        btnRemove = new JButton("Remove");
        pnlBtm.add(btnRemove);
        btnRemove.addActionListener((ActionEvent e) -> {
            /*
            //TODO:  surrond below in a for loop
            if (bal.remove()) {
                //TODO:  close window and return to prior window.
            } else {
                //TODO:  display bal.getErrorMessage() and stay on this window.
            }
             */
        });

        btnOK = new JButton("OK");
        pnlBtm.add(btnOK);
        btnOK.addActionListener((ActionEvent e) -> {
            /*
            //TODO:  surrond below in a for loop
            if (!bal.save()) {
                //TODO:  display bal.getErrorMessage();
            }
             */
        });

        btnCancel = new JButton("Cancel");
        pnlBtm.add(btnCancel);
        btnCancel.addActionListener((ActionEvent e) -> {
            //TODO:  close window and return to prior window.
        });

        //Finalizations
        pack();
    }

    private void getValues() {
        if (bll.load()) {
            this.orderItems.addAll(bll.getItems());
        }
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
    private class CloseQuery extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            JFrame frame = (JFrame) e.getSource();
            int result = JOptionPane.showConfirmDialog(frame,
                    "Do you want to save?", "Close Query",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                //TODO
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            } else {
                //TODO
            }
        }
    }
    // </editor-fold>
}
