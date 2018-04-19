package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import trackit.ASupplier;
import trackit.SuppliersTableModel;

/**
 * UI Layer: Handles all aspects of the Suppliers panel.
 *
 * @author Douglas
 */
public class SuppliersPanel
        extends JPanel {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    /**
     * The name of the panel.
     */
    public static final String TAB_NAME = "Suppliers";
    private static final String[] TABLE_LABELS = {"Supplier", "Web Address"};
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final ArrayList<ASupplier> suppliers = new ArrayList<>();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JButton btnCreate, btnRemove, btnEdit;
    JTable mainTable;
    private DefaultTableModel mainTableModel;
    private boolean disableButtons = false; //use this variable to toggle edit and remove buttons on and off
    SupplierDetailsDialog details;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Supplier UI
     */
    public SuppliersPanel() {
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
     * Toggles whether buttons will be enabled or not.
     */
    private void toggleDisableButton() {

        btnEdit.setEnabled(disableButtons);
        btnRemove.setEnabled(disableButtons);
    }
    /**
     * populates table data in a way that is dynamic
     */
    private void initTableData(ArrayList<ASupplier> suppliers){
        if(suppliers!=null){
            for(ASupplier sup : suppliers){
                Object[] data = {sup.getNickname(),sup.getUrl()};
                mainTableModel.addRow(data);
            }
        }
    }

    /**
     * Initializes all the GUI components
     */
    private void initializeComponents() {

        setLayout(new BorderLayout());
        //Declare the connection to DB
        SuppliersTableModel supplierConnection = new SuppliersTableModel();
        //init the table model. This will allow us to add data dynamically
        mainTableModel = new DefaultTableModel(TABLE_LABELS,0);
        // init the table itself
        mainTable = new JTable(mainTableModel);
        // declare scroll pane and add table to it
        JScrollPane suppliersScrollPane = new JScrollPane(mainTable);
        mainTable.setFillsViewportHeight(true);
        mainTable.setDefaultEditor(Object.class, null);

        // Add action listener to JTable
        mainTable.getSelectionModel().addListSelectionListener((e) -> {
            //if the row is bigger than -1 than we need to enable the buttons
            if (mainTable.getSelectedRow() > -1) {
                disableButtons = true;
                toggleDisableButton();
            }
        });

        //add data to table
        initTableData(supplierConnection.getSuppliers());


        add(suppliersScrollPane, BorderLayout.CENTER);

        JPanel btmSup = new JPanel();

        btnCreate = new JButton("Create");
        btnCreate.addActionListener((ActionEvent e) -> {
            //System.out.print("create supply");
            SupplierDetailsDialog dlgCreate = new SupplierDetailsDialog(true, null);
            dlgCreate.display();
        });

        btnEdit = new JButton("Edit");
        btnEdit.setEnabled(disableButtons);
        btnEdit.addActionListener((ActionEvent e) -> {
            //System.out.print("Edit supply");
            //If list item selected then edit item else select item.
            int selectedRow = mainTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Select item to edit");
            } else {
                ASupplier aSupplier = new ASupplier();
                //TODO: Set aSupplier to the value of selectedRow.
                SupplierDetailsDialog dlgEdit = new SupplierDetailsDialog(false, aSupplier);
                dlgEdit.display();
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
    
    public static String[] getColumnNames(){
        return TABLE_LABELS;   
    }
    
    /**
     * Displays the frame.
     *  
     */
    
    
    public void display() {
        setVisible(true);
    }
    // </editor-fold>
}
