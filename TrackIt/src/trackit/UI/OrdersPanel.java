package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import trackit.AnOrder;

/**
 * UI Layer: Handles all aspects of the Order panel.
 *
 * @author Douglas
 */
public class OrdersPanel
        extends JPanel {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    /**
     * The name of the panel.
     */
    public static final String TAB_NAME = "Orders";
    private static final String[] TABLE_LABELS = {"Order Date", "Order Number", "Supplier", "Status", "Total"};

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final ArrayList<AnOrder> orders = new ArrayList<>();
    private final AnOrder bll = new AnOrder();
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JButton btnCreate, btnRemove, btnEdit;
    JTable mainTable;
    OrderItemsFrame details;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default Constructor.
     */
    public OrdersPanel() {
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

    private void initializeComponents() {
        setLayout(new BorderLayout());

        //add data to suppliers arraylist 
        Object[][] testData = {{"12MAY2018", "019645232", "Walmart", "in transit", "$128.34"}, {"12MAY2018", "019645232", "Walmart", "in transit", "$128.34"}, {"12MAY2018", "019645232", "Walmart", "in transit", "$128.34"}};
        mainTable = new JTable(testData, TABLE_LABELS);
        JScrollPane scrollPane = new JScrollPane(mainTable);
        mainTable.setFillsViewportHeight(true);
        mainTable.setDefaultEditor(Object.class, null);

        add(scrollPane, BorderLayout.CENTER);

        JPanel btmSup = new JPanel();

        btnCreate = new JButton("Create");
        btnCreate.addActionListener((ActionEvent e) -> {
            System.out.print("create order");
            details = new OrderItemsFrame();
        });

        btnEdit = new JButton("Edit");
        btnEdit.addActionListener((ActionEvent e) -> {
            System.out.print("Edit order");
            //if list item selected edit item else select item
            int selectedRow = mainTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Select item to edit");
            } else {
                details = new OrderItemsFrame();
                //TODO: enter item info of selected item
            }
        });

        btnRemove = new JButton("Remove");
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

        btmSup.add(btnCreate);
        btmSup.add(btnEdit);
        btmSup.add(btnRemove);

        add(btmSup, BorderLayout.SOUTH);

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
    // <editor-fold defaultstate="collapsed" desc="SubClasses">

    // </editor-fold>
}
