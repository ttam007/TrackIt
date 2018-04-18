package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
    private boolean disableButtons = false;//use this variable to toggle edit and remove buttons on and off
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JButton btnCreate, btnRemove, btnEdit;
    JTable mainTable;
    OrderItemsFrame details;
    private DefaultTableModel mainTableModel;

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
     * Toggles whether buttons will be enabled or not.
     */
    private void toggleDisableButton() {

        btnEdit.setEnabled(disableButtons);
        btnRemove.setEnabled(disableButtons);
    }
    /**
     *
     */
    private void initTableData(){
        ArrayList<String> testConcept = new ArrayList<String>();

        testConcept.add("12MAY2018 , 019645232, Walmart, in transit, $128.34");
        testConcept.add("12MAY2018 , 019645232, Walmart, in transit, $128.34");
        testConcept.add("12MAY2018 , 019645232, Walmart, in transit, $128.34");
        testConcept.add("12MAY2018 , 019645232, Walmart, in transit, $128.34");
        testConcept.add("12MAY2018 , 019645232, Walmart, in transit, $128.34");
        testConcept.add("12MAY2018 , 019645232, Walmart, in transit, $128.34");
        testConcept.add("12MAY2018 , 019645232, Walmart, in transit, $128.34");
        testConcept.add("12MAY2018 , 019645232, Walmart, in transit, $128.34");


        for(String e : testConcept){
            String[] test = e.split(",");
            Object[] data = {test[0],test[1],test[2],test[3],test[4]};
            mainTableModel.addRow(data);

        }
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
        mainTableModel= new DefaultTableModel(TABLE_LABELS,0);
        setLayout(new BorderLayout());

        //add data to suppliers arraylist 
        //Object[][] testData = {{"12MAY2018", "019645232", "Walmart", "in transit", "$128.34"}, {"12MAY2018", "019645232", "Walmart", "in transit", "$128.34"}, {"12MAY2018", "019645232", "Walmart", "in transit", "$128.34"}};
        //mainTable = new JTable(testData, TABLE_LABELS);
        mainTable = new JTable(mainTableModel);

        JScrollPane scrollPane = new JScrollPane(mainTable);
        mainTable.setFillsViewportHeight(true);
        mainTable.setDefaultEditor(Object.class, null);


        mainTable.getSelectionModel().addListSelectionListener((e) -> {
            //if the row is bigger than -1 than we need to enable the buttons
            if (mainTable.getSelectedRow() > -1) {
                disableButtons = true;
                toggleDisableButton();
            }
        });

        initTableData();




        add(scrollPane, BorderLayout.CENTER);

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

                details = new OrderItemsFrame();
                //TODO: enter item info of selected item

        });

        btnRemove = new JButton("Remove");
        btnRemove.setEnabled(disableButtons);
        btnRemove.addActionListener((ActionEvent e) -> {




                //TODO: remove item from db
                JOptionPane.showMessageDialog(null, "Item removed");

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
