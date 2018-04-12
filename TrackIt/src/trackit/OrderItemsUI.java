package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import trackit.*;

/**
 * @author Douglas
 * UI Layer: Handles all aspects of the Order Details dialog. This is a
 * combination of the Edit Order Details and the OrderItems grid.
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
        int frameWidth = 1200;
        int frameHeight = 600;
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());

        //Add all components here and set properties.
        setLayout(new BorderLayout());
        
        pnlTop = new JPanel();
        //layGroup order-details
        
        //add(pnlTop, BorderLayout.);
        
        //add data to suppliers arraylist 
        Object[][] suppliersTestData = {{"12MAY2018", "019645232", "Walmart", "in transit", "$128.34", ""}, {"12MAY2018", "019645232", "Walmart", "in transit", "$128.34", ""}, {"12MAY2018", "019645232", "Walmart", "in transit", "$128.34", ""} };
        ordersTable = new JTable(suppliersTestData, ordersLabel);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        ordersTable.setFillsViewportHeight(true);
        ordersTable.setDefaultEditor(Object.class, null);
        
        add(scrollPane, BorderLayout.CENTER);
        
        pnlBtm = new JPanel();
        
        btnCreate = new JButton("Create");
        
        
        this.add(this.btnOK);
        this.btnOK.addActionListener((ActionEvent e) -> {
            /*
            //TODO:  surrond below in a for loop
            if (!bal.save()) {
                //TODO:  display bal.getErrorMessage();
            }
             */
        });
        this.add(this.btnCancel);
        this.btnCancel.addActionListener((ActionEvent e) -> {
            //TODO:  close window and return to prior window.
        });
        this.add(this.btnRemove);
        this.btnRemove.addActionListener((ActionEvent e) -> {
            /*
            //TODO:  surrond below in a for loop
            if (bal.remove()) {
                //TODO:  close window and return to prior window.
            } else {
                //TODO:  display bal.getErrorMessage() and stay on this window.
            }
             */
        });
        this.add(this.btnAddItem);
        this.btnAddItem.addActionListener((ActionEvent e) -> {
            //TODO
        });
        this.add(this.btnRemove);
        this.btnRemove.addActionListener((ActionEvent e) -> {
            //TODO
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