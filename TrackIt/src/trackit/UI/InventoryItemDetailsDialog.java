package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jdatepicker.impl.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Create Inventory Item and Edit Inventory
 * Item dialog.
 *
 * @author Bond, Steven, Diaz
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
    private final Inventory bll = new Inventory();
    private DialogResultType dialogResult = DialogResultType.NONE;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    private JComboBox<ItemStatusType> cboItemStatus;
    private JTextField tfSku, tfSizeUnit, tfDescription;
    private JFormattedTextField tfQuantity;
    private JLabel sku, statusLabel, unit, expDateLbl, quantity, itemNameLabel;
    private JButton btnOK, btnCancel;
    private GridBagConstraints gbc;
    private JDatePickerImpl expDatePicker;
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
            this.anInventoryItem = new AnInventoryItem();
        } else if (anInventoryItem == null) {
            throw new IllegalArgumentException("When 'useCreateMode' = false, then a non-null anInventoryItem must be provided.");
        } else {
            this.anInventoryItem = anInventoryItem;
        }

        initializeComponents();
        populateComponents();
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
        int frameWidth = 500;
        int frameHeight = 250;
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());
        this.getRootPane().setDefaultButton(btnOK);
        this.setModal(true);

        //Add all components here and set properties.
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
        tfDescription = new JTextField(25);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        add(tfDescription, gbc);

        // Initialize Sku label and text field
        sku = new JLabel("SKU: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(sku, gbc);
        tfSku = new JTextField(25);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        add(tfSku, gbc);
        // Init Quantity
        //Label
        quantity = new JLabel("Quantity: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(quantity, gbc);
        // Field
        tfQuantity = new JFormattedTextField(Utilities.getIntegerFormatter());
        tfQuantity.setEditable(this.isCreateMode);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(tfQuantity, gbc);
        // Init Exp Date Label and Field
        expDateLbl = new JLabel("Exp Date: ");
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(expDateLbl, gbc);
        expDatePicker = Utilities.getDatePicker();

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
        tfSizeUnit = new JTextField(7);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        add(tfSizeUnit, gbc);

        // Init Status Label and Field
        statusLabel = new JLabel("Status: ");
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(statusLabel, gbc);

        //Text Field
        cboItemStatus = new JComboBox<>(ItemStatusType.values());
        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(cboItemStatus, gbc);

        // Init Ok Button
        btnOK = new JButton(Utilities.BUTTON_OK);
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(btnOK, gbc);
        this.btnOK.addActionListener((ActionEvent e) -> {
            saveAction();
        });

        //Cancel
        btnCancel = new JButton(Utilities.BUTTON_CANCEL);
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(btnCancel, gbc);
        this.btnCancel.addActionListener((ActionEvent e) -> {
            cancelAction();
        });

        pack();
    }

    /**
     * Populates all the UI components from the object in memory.
     */
    private void populateComponents() {
        this.tfDescription.setText(this.anInventoryItem.getDescription());
        this.tfSku.setText(this.anInventoryItem.getSku());
        this.tfSizeUnit.setText(this.anInventoryItem.getSizeUnit());
        this.tfQuantity.setValue(this.anInventoryItem.getQuantity());
        this.cboItemStatus.getModel().setSelectedItem(this.anInventoryItem.getItemStatus());
        Utilities.setDatePickersDate(this.expDatePicker, this.anInventoryItem.getExpirationDate());
    }

    /**
     * Populates the object in memory from all the UI components.
     */
    private boolean populateObject() {
        boolean returnValue = false;
        //TODO:  sort this out so boolean return is used instead of try/catch block.
        try {
            this.anInventoryItem.setDescription(this.tfDescription.getText());
            this.anInventoryItem.setSku(this.tfSku.getText());
            this.anInventoryItem.setSizeUnit(this.tfSizeUnit.getText());
            this.anInventoryItem.setQuantity((Integer) this.tfQuantity.getValue());
            this.anInventoryItem.setItemStatus((ItemStatusType) this.cboItemStatus.getModel().getSelectedItem());
            this.anInventoryItem.setExpirationDate(Utilities.getDatePickersDate(this.expDatePicker));
            returnValue = true;
        } catch (java.sql.SQLException | RuntimeException ex) {
            Utilities.setErrorMessage(ex);
            JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        }
        return returnValue;
    }

    /**
     * Handles the save action. If any errors, then display error message
     * instead.
     */
    private void saveAction() {
        if (populateObject()) {
            if (this.bll.save(this.anInventoryItem)) {
                this.dialogResult = DialogResultType.OK;
                this.setVisible(false);
                this.dispose();
            } else {
                this.dialogResult = DialogResultType.CANCEL;
                JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                        Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handles the cancel action.
     */
    private void cancelAction() {
        this.dialogResult = DialogResultType.CANCEL;
        this.setVisible(false);
        this.dispose();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Displays the dialog.
     *
     * @return The DialogReturnType which tells how the dialog was closed.
     */
    public DialogResultType display() {
        setVisible(true);
        return this.dialogResult;
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
