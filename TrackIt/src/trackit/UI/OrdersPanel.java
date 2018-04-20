package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import trackit.AnOrder;
import trackit.Orders;

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
    private static final String[] TABLE_LABELS = {"Order Date", "Order Number", "Supplier", "Status", "Total"};
    private final ArrayList<AnOrder> orders = new ArrayList<>();

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final AnOrder bll = new AnOrder();
    private JButton btnCreate, btnRemove, btnEdit;
    private JTable mainTable;
    private DefaultTableModel mainTableModel;
    private JScrollPane sp;
    private boolean disableButtons = false;//use this variable to toggle edit and remove buttons on and off
    private OrderItemsFrame details;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">

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
        mainTableModel = new DefaultTableModel(TABLE_LABELS, 0);
        Orders test = new Orders();
        mainTable = new JTable(mainTableModel);
        mainTable.setEnabled(false);
        // Add action listener to JTable
        mainTable.getSelectionModel().addListSelectionListener((e) -> {
            //if the row is bigger than -1 than we need to enable the buttons
            if (mainTable.getSelectedRow() > -1) {
                disableButtons = true;
                toggleDisableButton();
            }
        });
        mainTable.setBounds(30, 40, 200, 200);
        initTableData(test.getSQL());

        sp = new JScrollPane(mainTable);

        add(sp, BorderLayout.CENTER);

        add(sp, BorderLayout.CENTER);

        JPanel btmSup = new JPanel();

        btnCreate = new JButton("Create");

        btnCreate.addActionListener((ActionEvent e) -> {
            System.out.print("create order");
            details = new OrderItemsFrame();
        });

        btnEdit = new JButton("Edit");
        btnEdit.setEnabled(disableButtons);
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

        btmSup.add(btnCreate);
        btmSup.add(btnEdit);
        btmSup.add(btnRemove);

        add(btmSup, BorderLayout.SOUTH);

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    public static String[] getColumnNames() {
        return TABLE_LABELS;
    }

    private void initTableData(ArrayList<AnOrder> test) {
        System.out.println(test);

        for (AnOrder e : test) {
            Object[] data = {e.getDescription(), e.getOrderedFrom(), e.getOrderStatus(), e.getDateOrdered(), e.getDateExpected()};
            mainTableModel.addRow(data);
        }
    }

    private void toggleDisableButton() {
        btnEdit.setEnabled(disableButtons);
        btnRemove.setEnabled(disableButtons);
    }

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
