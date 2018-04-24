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
    private boolean makeButtonsEnabled = false;
    //SupplierDetailsDialog details;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    private JButton btnCreate, btnRemove, btnEdit;
    private JTable mainTable;
    private DefaultTableModel mainTableModel;
    private JScrollPane sp;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Supplier UI
     */
    public SuppliersPanel() {
        initializeComponents();
        refreshGrid();
        toggleDisableButton();
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

        mainTableModel = new DefaultTableModel(TABLE_LABELS, 0);
        mainTable = new JTable(mainTableModel);
        mainTable.setDefaultEditor(Object.class, null);
        mainTable.getTableHeader().setReorderingAllowed(false);
        mainTable.getSelectionModel().addListSelectionListener((e) -> {
            //if the row is bigger than -1 than we need to enable the buttons
            if (mainTable.getSelectedRow() > -1) {
                makeButtonsEnabled = true;
                toggleDisableButton();
            }
        });
        mainTable.addMouseListener(new MouseAdapter() {
            /**
             * https://stackoverflow.com/questions/14852719/double-click-listener-on-jtable-in-java
             *
             * @param mouseEvent
             */
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2) {// && table.getSelectedRow() != -1) {
                    editAction();
                }
            }
        });
        mainTable.setBounds(30, 40, 200, 200);

        sp = new JScrollPane(mainTable);

        add(sp, BorderLayout.CENTER);

        JPanel btmSup = new JPanel(new GridLayout(0, 8, 2, 0));

        btnCreate = new JButton(Utilities.BUTTON_CREATE);
        btnCreate.addActionListener((ActionEvent e) -> {
            SupplierDetailsDialog dlgCreate = new SupplierDetailsDialog(true, null);
            dlgCreate.setLocationRelativeTo(this);
            if (dlgCreate.display() == DialogResultType.OK) {
                this.refreshGrid();
            }
        });

        btnEdit = new JButton(Utilities.BUTTON_EDIT);
        btnEdit.addActionListener((ActionEvent e) -> {
            editAction();
        });

        btnRemove = new JButton(Utilities.BUTTON_REMOVE);
        btnRemove.addActionListener((ActionEvent e) -> {
            removeAction();
        });

        btmSup.add(btnCreate);
        btmSup.add(btnEdit);
        btmSup.add(btnRemove);

        add(btmSup, BorderLayout.PAGE_END);
    }

    /**
     * Toggles whether buttons will be enabled or not.
     */
    private void toggleDisableButton() {
        btnEdit.setEnabled(makeButtonsEnabled);
        btnRemove.setEnabled(makeButtonsEnabled);
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

    /**
     * Refreshes the grid with current data from the database.
     */
    public void refreshGrid() {
        //Clear the ArrayList and JTable, which should be done backwards.
        this.suppliers.clear();
        for (int i = mainTableModel.getRowCount() - 1; i >= 0; i--) {
            mainTableModel.removeRow(i);
        }

        //Now load fresh data from database.
        if (this.bll.load()) {
            ArrayList<ASupplier> aList = this.bll.getList();
            initTableData(aList);
        } else {
            JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Pops the detail item dialog if an item is selected.
     */
    private void editAction() {
        int selectedRow = mainTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select item to edit");
        } else {
            ASupplier aSupplier = this.suppliers.get(selectedRow);
            SupplierDetailsDialog dlgEdit = new SupplierDetailsDialog(false, aSupplier);
            dlgEdit.setLocationRelativeTo(this);
            if (dlgEdit.display() == DialogResultType.OK) {
                this.refreshGrid();
            }
        }
    }

    /**
     * Handles removing a Supplier from the database.
     */
    private void removeAction() {
        int selectedRow = this.mainTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Select item to remove");
        } else {
            ASupplier aSupplier = this.suppliers.get(selectedRow);
            if (this.bll.remove(aSupplier.getPrimaryKey())) {
                this.refreshGrid();
                JOptionPane.showMessageDialog(null,
                        String.format("%s has been removed.", aSupplier.getNickname()));
            } else {
                JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                        Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
            }
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
