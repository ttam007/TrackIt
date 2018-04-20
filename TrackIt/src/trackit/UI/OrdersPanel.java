package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
<<<<<<< HEAD
import trackit.AnOrder;
import trackit.OrdersTableModel;
=======
import javax.swing.table.DefaultTableModel;
import trackit.*;

>>>>>>> Dev

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
<<<<<<< HEAD
    private static final String[] TABLE_LABELS = {"Order Date", "Order Number", "Supplier", "Status", "Total"};

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final ArrayList<AnOrder> orders = new ArrayList<>();
    private final AnOrder bll = new AnOrder();
=======
    private static final String[] TABLE_LABELS = {"Description", "Supplier", "Status", "Order Date", "Expected Date"};

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private HashMap<Integer, AnOrder> orders = new HashMap<>();
    Orders bll = new Orders();
    
    private JButton btnCreate, btnRemove, btnEdit;
    private JTable mainTable;
    private DefaultTableModel mainTableModel;
    private JScrollPane sp;
    private boolean disableButtons = false;//use this variable to toggle edit and remove buttons on and off
>>>>>>> Dev
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
        refreshItems();
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

<<<<<<< HEAD
        //add data to suppliers arraylist 
        Object[][] testData = {{"12MAY2018", "019645232", "Walmart", "in transit", "$128.34"}, {"12MAY2018", "019645232", "Walmart", "in transit", "$128.34"}, {"12MAY2018", "019645232", "Walmart", "in transit", "$128.34"}};
        mainTable = new JTable(new OrdersTableModel());
        JScrollPane scrollPane = new JScrollPane(mainTable);
        mainTable.setFillsViewportHeight(true);
        mainTable.setDefaultEditor(Object.class, null);
=======
        //add data to suppliers arraylist
        mainTableModel = new DefaultTableModel(TABLE_LABELS, 0);
        mainTable = new JTable(mainTableModel);
        mainTable.setDefaultEditor(Object.class, null);
        mainTable.getTableHeader().setReorderingAllowed(false);
        // Add action listener to JTable
        mainTable.getSelectionModel().addListSelectionListener((e) -> {
            //if the row is bigger than -1 than we need to enable the buttons
            if (mainTable.getSelectedRow() > -1) {
                disableButtons = true;
                toggleDisableButton();
            }
        });
        mainTable.setBounds(30, 40, 200, 200);
>>>>>>> Dev

        add(scrollPane, BorderLayout.CENTER);

        JPanel btmSup = new JPanel();

        btnCreate = new JButton("Create");
        btnCreate.addActionListener((ActionEvent e) -> {
            OrderItemsFrame dlgCreate = new OrderItemsFrame(true, null);
            dlgCreate.setLocationRelativeTo(sp);
            if (dlgCreate.display() == DialogResultType.OK) {
                this.refreshItems();
            }
        });

        btnEdit = new JButton("Edit");
        btnEdit.addActionListener((ActionEvent e) -> {
            System.out.print("Edit order");
            //if list item selected edit item else select item
            int selectedRow = mainTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Select item to edit");
            } else {
                AnOrder anOrder = this.orders.get(selectedRow);
                OrderItemsFrame dlgEdit = new OrderItemsFrame(false, anOrder);
                dlgEdit.setLocationRelativeTo(sp);
                if (dlgEdit.display() == DialogResultType.OK) {
                    this.refreshItems();
                }
            }
        });

        btnRemove = new JButton("Remove");
        btnRemove.addActionListener((ActionEvent e) -> {
            int selectedRow = this.mainTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Select item to remove");
            } else {
                AnOrder anOrder = this.orders.get(selectedRow);
                if (this.bll.remove(anOrder.getPrimaryKey())) {
                    this.refreshItems();
                    JOptionPane.showMessageDialog(null,
                            String.format("%s has been removed.", anOrder.getDescription()));
                } else {
                    JOptionPane.showMessageDialog(this, this.bll.getErrorMessage(),
                            Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
                }
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
<<<<<<< HEAD
   public static String[] getColumnNames(){
        return TABLE_LABELS;   
    }
    
=======
  

    private void toggleDisableButton() {
        btnEdit.setEnabled(disableButtons);
        btnRemove.setEnabled(disableButtons);
    }
    
    private void initTableData(ArrayList<AnOrder> aList) {
        if (this.orders != null) {
            int counter = 0;
            for (AnOrder anOrder : aList) {
                Object[] data = {anOrder.getDescription(), anOrder.getOrderedFrom(), anOrder.getDateOrdered(), anOrder.getOrderStatus(), anOrder.getDateExpected()};
                mainTableModel.addRow(data);
                this.orders.put(counter, anOrder);
                counter++;
            }
        }
    }
    
    private void refreshItems() {
        //Clear the ArrayList and JTable, which should be done backwards.
        this.orders.clear();
        for (int i = mainTableModel.getRowCount() - 1; i >= 0; i--) {
            mainTableModel.removeRow(i);
        }

        //Now load fresh data from database.
        if (bll.load()) {
            ArrayList<AnOrder> aList = bll.getList();
            initTableData(aList);
        } else {
            JOptionPane.showMessageDialog(this, bll.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        }
    }
>>>>>>> Dev
    
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
