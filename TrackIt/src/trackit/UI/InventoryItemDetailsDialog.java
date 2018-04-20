package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Properties;
import javax.swing.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Create Inventory Item and Edit Inventory
 * Item dialog.
 *
 * @author Bond, Steven
 */
public class InventoryItemDetailsDialog
        extends JDialog {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    /**
     * The name of the dialog.
     */
    public static final String WINDOW_NAME = "Inventory Item Details";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final boolean isCreateMode;
    private final AnInventoryItem anInventoryItem;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    private JTextField skuField, quantityField, unitField, statusField, itemNameField;
    private JLabel sku, statusLabel, unit, expDateLbl, quantity, itemNameLabel;
    private JButton btnOK, btnCancel;
    private GridBagConstraints gbc;
    private Date expDate, sqlExpDate;
    
    UtilDateModel expModel = new UtilDateModel();
    JDatePanelImpl expDatePanel;
    JDatePickerImpl expDatePicker;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Creates a new dialog for working with a single Inventory Item.
     *
     * @param useCreateMode True = use for a new Inventory Item; False = use to
     * edit the specified Inventory Item.
     * @param anInventoryItem The Inventory Item to be edited. This value is
     * ignored if useCreateMode is true.
     */
    public InventoryItemDetailsDialog(boolean useCreateMode, AnInventoryItem anInventoryItem) {
        this.isCreateMode = useCreateMode;
        if (this.isCreateMode) {
            this.anInventoryItem = null;
        } else if (anInventoryItem == null) {
            throw new IllegalArgumentException("When 'useCreateMode' = true, then a non-null anInventoryItem must be provided.");
        } else {
            this.anInventoryItem = anInventoryItem;
        }

        this.initializeComponents();
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
     * Sets up all components used in this frame.
     */
    private void initializeComponents() {
        //Setup main frame
        int frameWidth = 500;// Originally 640
        int frameHeight = 250;//Originally 400.
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setSize(dimFrame);
        this.setPreferredSize(dimFrame);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());
        this.getRootPane().setDefaultButton(btnOK);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        expDatePanel = new JDatePanelImpl(expModel,p);

        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        gbc.insets = new Insets(2, 2, 5, 0);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Item Name Label Initialized
        itemNameLabel = new JLabel("Item Name: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(itemNameLabel, gbc);

        // Item Name Text Field
        itemNameField = new JTextField(25);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        add(itemNameField, gbc);

        // Initialize Sku label and text field
        sku = new JLabel("SKU: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(sku, gbc);
        skuField = new JTextField(25);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        add(skuField, gbc);
        // Init Quantity
        //Label
        quantity = new JLabel("Quantity: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(quantity, gbc);
        // Field
        quantityField = new JTextField(7);
        quantityField.setEditable(this.isCreateMode);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(quantityField, gbc);
        // Init Exp Date Label and Field
        expDateLbl = new JLabel("Exp Date: ");
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(expDateLbl, gbc);
        expDatePicker = new JDatePickerImpl(expDatePanel, new DateLabelFormatter());

        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(expDatePicker, gbc);
        // Unit
        //Label
        unit = new JLabel("Unit: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(unit, gbc);
        // Field
        unitField = new JTextField(7);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        add(unitField, gbc);

        // Init Status Label and Field
        statusLabel = new JLabel("Status: ");
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(statusLabel, gbc);
        //Text Field
        statusField = new JTextField(7);

        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(statusField, gbc);

        // Init Ok Button
        btnOK = new JButton("Ok");
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(btnOK, gbc);
        btnOK.addActionListener((ActionEvent e) -> {
            saveAction();
        });

        //Cancel
        btnCancel = new JButton("Cancel");
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(btnCancel, gbc);
        btnCancel.addActionListener((ActionEvent e) -> {
            cancelAction();
        });

        this.pack();
    }

    /**
     * Handles the save action. If any errors, then display error message
     * instead.
     *
     */
    private void saveAction() {
        JOptionPane.showMessageDialog(null, "Successfully Updated");
        
        expDate = (Date) expDatePicker.getModel().getValue();
            sqlExpDate = Utilities.convertToSQLDate(expDate);
            System.out.println(sqlExpDate);

//TODO:  implement save.
        /*if (successfullySaved) {
                this.dispose();
            } else {
               //TODO:  catch errors and display them.  Do not exit dialog if an error occurs.
            }*/
        this.dispose();
    }

    /**
     * Handles the cancel action. If any errors, then display error message
     * instead.
     *
     */
    private void cancelAction() {
        JOptionPane.showMessageDialog(null, "Change Cancelled");
        //TODO:  close window and return to prior window.
        this.dispose();
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
            JDialog frame = InventoryItemDetailsDialog.this;
            int result = JOptionPane.showConfirmDialog(frame,
                    "Do you want to save?", "Close Query",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                saveAction();
            } else {
                cancelAction();
            }
        }
    }
    // </editor-fold>
}
