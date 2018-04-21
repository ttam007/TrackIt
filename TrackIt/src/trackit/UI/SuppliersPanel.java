package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Suppliers panel.
 *
 * @author Douglas, Diaz, Steven
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
    private final HashMap<Integer, ASupplier> suppliers = new HashMap<>();
    private final Suppliers bll = new Suppliers();
    /**
     * Use this variable to toggle edit and remove buttons on and off.
     */
    private boolean disableButtons = false;
    //SupplierDetailsDialog details;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    private JTable mainTable;
    private JButton btnCreate, btnRemove, btnEdit;
    private DefaultTableModel mainTableModel;
    private JScrollPane sp;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Supplier UI
     */
    public SuppliersPanel() {
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

        //add data to suppliers arraylist
        mainTableModel = new DefaultTableModel(TABLE_LABELS, 0);

        // mainTable = new JTable(data, TABLE_LABELS);
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

        sp = new JScrollPane(mainTable);

        add(sp, BorderLayout.CENTER);

        JPanel btmSup = new JPanel(new GridLayout(0, 8, 2, 0));

        btnCreate = new JButton("Create");
        btnCreate.addActionListener((ActionEvent e) -> {
            SupplierDetailsDialog dlgCreate = new SupplierDetailsDialog(true, null);
            dlgCreate.setLocationRelativeTo(sp);
            if (dlgCreate.display() == DialogResultType.OK) {
                this.refreshItems();
            }
        });

        btnEdit = new JButton("Edit");
        btnEdit.setEnabled(disableButtons);
        btnEdit.addActionListener((ActionEvent e) -> {
            //If list item selected then edit item else select item.
            int selectedRow = mainTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Select item to edit");
            } else {
                ASupplier aSupplier = this.suppliers.get(selectedRow);
                SupplierDetailsDialog dlgEdit = new SupplierDetailsDialog(false, aSupplier);
                dlgEdit.setLocationRelativeTo(sp);
                if (dlgEdit.display() == DialogResultType.OK) {
                    this.refreshItems();
                }
            }
        });

        btnRemove = new JButton("Remove");
        btnRemove.setEnabled(disableButtons);
        btnRemove.addActionListener((ActionEvent e) -> {
            int selectedRow = this.mainTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Select item to remove");
            } else {
                ASupplier aSupplier = this.suppliers.get(selectedRow);
                if (this.bll.remove(aSupplier.getPrimaryKey())) {
                    this.refreshItems();
                    JOptionPane.showMessageDialog(null,
                            String.format("%s has been removed.", aSupplier.getNickname()));
                } else {
                    JOptionPane.showMessageDialog(this, this.bll.getErrorMessage(),
                            Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btmSup.add(btnCreate);
        btmSup.add(btnEdit);
        btmSup.add(btnRemove);

        add(btmSup, BorderLayout.PAGE_END);
    }

    private void toggleDisableButton() {
        btnEdit.setEnabled(disableButtons);
        btnRemove.setEnabled(disableButtons);
    }

    private void initTableData(ArrayList<ASupplier> aList) {
        if (this.suppliers != null) {
            int counter = 0;
            for (ASupplier aSupplier : aList) {
                Object[] data = {aSupplier.getNickname(), aSupplier.getUrl()};
                mainTableModel.addRow(data);
                this.suppliers.put(counter, aSupplier);
                counter++;
            }
        }
    }

    private void refreshItems() {
        //Clear the ArrayList and JTable, which should be done backwards.
        this.suppliers.clear();
        for (int i = mainTableModel.getRowCount() - 1; i >= 0; i--) {
            mainTableModel.removeRow(i);
        }

        //Now load fresh data from database.
        if (bll.load()) {
            ArrayList<ASupplier> aList = bll.getList();
            initTableData(aList);
        } else {
            JOptionPane.showMessageDialog(this, bll.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Displays the frame.
     *
     */
    public void display() {
        setVisible(true);
    }

    /**
     * Gets the array of table column headers.
     *
     * @return The array of column headers.
     */
    public static String[] getColumnHeaders() {
        return TABLE_LABELS.clone();
    }
    // </editor-fold>
}
