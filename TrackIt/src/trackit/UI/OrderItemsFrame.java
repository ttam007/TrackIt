package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import trackit.*;
import trackit.DAL.*;

/**
 * UI Layer: Handles all aspects of the AnOrder Details dialog. This is a
 * combination of the Edit AnOrder Details and the OrderItems grid.
 *
 * @author Douglas, Bond
 */
public class OrderItemsFrame
        extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    private static final String WINDOW_NAME = "Order Details";
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
    private final String[] ordersLabel = {"Item Name", "Unit", "SKU", "Quantity", "Price", "Ext Price"};
    private JTable ordersTable;
    private int selectedRow;

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
        tfOrderDate = new JTextField(20);
        topInnerBx.add(tfOrderDate);

        btmInnerBx = Box.createHorizontalBox();
        lblStatus = new JLabel("            Status:");
        btmInnerBx.add(lblStatus);
        tfStatus = new JTextField(20);
        btmInnerBx.add(tfStatus);

        lblStatus = new JLabel("                          ");
        btmInnerBx.add(lblStatus);

        lblExpectedDate = new JLabel("                                                     Expected Date:");
        btmInnerBx.add(lblExpectedDate);
        tfExpectedDate = new JTextField(20);
        btmInnerBx.add(tfExpectedDate);

        topBox.add(topInnerBx);
        topBox.add(btmInnerBx);

        add(topBox, BorderLayout.NORTH);

        middleBox = Box.createHorizontalBox();

        btnCheckIn = new JButton("Check In");
        middleBox.add(btnCheckIn);
        btnCheckIn.addActionListener((ActionEvent e) -> {
            //TODO
            JOptionPane.showMessageDialog(null, "Item Checked In");
        });

        btnCheckInAll = new JButton("Check In All");
        middleBox.add(btnCheckInAll);
        btnCheckInAll.addActionListener((ActionEvent e) -> {
            //TODO
            JOptionPane.showMessageDialog(null, "All Items Checked In");
        });

        bottomBox = Box.createHorizontalBox();

        //add data to suppliers arraylist 
        Object[][] testData = {{"paper", "pk", "12-34563487-0", "7", "$12.95", "$276.23"}, {"paper", "pk", "12-34563487-0", "7", "$12.95", "$276.23"}, {"paper", "pk", "12-34563487-0", "7", "$12.95", "$276.23"}};
        ordersTable = new JTable(testData, ordersLabel);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        ordersTable.setFillsViewportHeight(true);
        ordersTable.setDefaultEditor(Object.class, null);

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
            //TODO
            OrderItemDetailsDialog oid = new OrderItemDetailsDialog(true);
            oid.display();
        });

        btnCreate = new JButton("Create");
        pnlBtm.add(btnCreate);
        btnCreate.addActionListener((ActionEvent e) -> {
            InventoryItemDetailsDialog iidAddItem = new InventoryItemDetailsDialog(true);
            iidAddItem.display();
        });

        btnEdit = new JButton("Edit");
        pnlBtm.add(btnEdit);
        btnEdit.addActionListener((ActionEvent e) -> {
            //TODO
            OrderItemDetailsDialog oid = new OrderItemDetailsDialog(false);
            oid.display();
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
            this.dispose();
        });

        btnCancel = new JButton("Cancel");
        pnlBtm.add(btnCancel);
        btnCancel.addActionListener((ActionEvent e) -> {
            //TODO:  close window and return to prior window.
            this.dispose();
        });

        add(pnlBtm, BorderLayout.SOUTH);

        //Finalizations
        pack();
    }

    private void getValues() {
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
    private static class CloseQuery
            extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            JFrame frame = (JFrame) e.getSource();
            int result = JOptionPane.showConfirmDialog(frame,
                    "Do you want to save?", "Close Query",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                //TODO
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.dispose();
            } else {
                //TODO
                frame.dispose();
            }
        }
    }
    // </editor-fold>
}
