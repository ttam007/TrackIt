package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Add Item to Order and Edit Order Item
 * dialog.
 */
public class OrderItemDetailsDialog
        extends JDialog {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    /**
     * The name of the dialog.
     */
    public static final String WINDOW_NAME = "Order Item Details";

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final boolean isCreateMode;
    private final AnOrderItem anOrderItem;
    private final OrderItems bll = new OrderItems();
    private DialogResultType dialogResult = DialogResultType.NONE;
    private final Inventory bllInventory = new Inventory();
    private final HashMap<String, AnInventoryItem> inventory = new HashMap<>();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    private JComboBox<String> cboStatus;
    private JComboBox<String> cboName;
    JPanel pnlCenter;
    JLabel lblName, lblQuantity, lblPrice, lblStatus, lblExtPrice;
    JTextField tfQuantityOrdered, tfPrice, tfExtPrice;
    JButton btnOK, btnCancel;
    GridBagConstraints gbc;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates a new dialog for working with a single Order Item.
     *
     * @param useCreateMode True = use for a new Order Item; False = use to edit
     * the specified Order Item.
     * @param anOrderItem The Order Item to be edited. This value is ignored if
     * useCreateMode is true.
     */
    public OrderItemDetailsDialog(boolean useCreateMode, AnOrderItem anOrderItem) {
        this.isCreateMode = useCreateMode;
        if (this.isCreateMode) {
            this.anOrderItem = new AnOrderItem();
        } else if (anOrderItem == null) {
            throw new IllegalArgumentException("When 'useCreateMode' = false, then a non-null anOrderItem must be provided.");
        } else {
            this.anOrderItem = anOrderItem;
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
     * initialize the items
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

        //GridBag Setup
        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        gbc.insets = new Insets(2, 2, 5, 0);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Description Label
        lblName = new JLabel("Item Name: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblName, gbc);

        //Description
        //TODO:  Implement this or something like it:  http://www.algosome.com/articles/java-jcombobox-autocomplete.html
        cboName = new JComboBox<>(getItemList());
        cboName.setEnabled(isCreateMode);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        add(cboName, gbc);

        //Quantity Ordered Label
        lblQuantity = new JLabel("Quantity: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(lblQuantity, gbc);

        //Quantity Ordered
        tfQuantityOrdered = new JTextField(2);
        tfQuantityOrdered.addFocusListener(new ExtendedPriceUpdater());
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        add(tfQuantityOrdered, gbc);

        //Price Label
        lblPrice = new JLabel("Price: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(lblPrice, gbc);

        //Price
        tfPrice = new JTextField(7);
        tfPrice.addFocusListener(new ExtendedPriceUpdater());
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(tfPrice, gbc);

        //Status Label
        lblStatus = new JLabel("Status: ");
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(lblStatus, gbc);

        //Status
        cboStatus = new JComboBox<>(ItemStatusType.getTextForAll());
        cboStatus.setEnabled(false);
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(cboStatus, gbc);

        //Extended Price Label
        lblExtPrice = new JLabel("Ext Price: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(lblExtPrice, gbc);

        //Extended Price
        tfExtPrice = new JTextField(7);
        tfExtPrice.setEnabled(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        add(tfExtPrice, gbc);

        //Ok Button
        btnOK = new JButton(Utilities.BUTTON_OK);
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(btnOK, gbc);
        btnOK.addActionListener((ActionEvent e) -> {
            saveAction();
        });

        //Cancel Button
        btnCancel = new JButton(Utilities.BUTTON_CANCEL);
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(btnCancel, gbc);
        btnCancel.addActionListener((ActionEvent e) -> {
            cancelAction();
        });

        //Finalizations
        pack();
    }

    /**
     * Populates all the UI components from the object in memory.
     */
    private void populateComponents() {
        this.cboName.getEditor().setItem(this.anOrderItem.getDescription());
        this.cboStatus.getEditor().setItem(this.anOrderItem.getItemStatus());
        this.tfQuantityOrdered.setText(this.anOrderItem.getQuantityOrdered().toString());
        this.tfPrice.setText(this.anOrderItem.getPrice().toString());
        this.tfExtPrice.setText(this.anOrderItem.getExtendedPrice().toString());
    }

    /**
     * Populates the object in memory from all the UI components.
     */
    private boolean populateObject() {
        boolean returnValue = false;
        //TODO:  sort this out so boolean return is used instead of try/catch block.
        try {
            this.anOrderItem.setDescription(this.cboName.getEditor().getItem().toString());
            this.anOrderItem.setItemId(0);
            this.anOrderItem.setQuantityOrdered(Integer.parseInt(this.tfQuantityOrdered.getText()));
            this.anOrderItem.setPrice(Double.parseDouble(this.tfPrice.getText()));
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
            if (this.bll.save(this.anOrderItem)) {
                this.dialogResult = DialogResultType.OK;
                //JOptionPane.showMessageDialog(null, "Successfully Saved.");
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
        //JOptionPane.showMessageDialog(null, "Change Cancelled");
        this.dialogResult = DialogResultType.CANCEL;
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Gets the list of all items in inventory.
     *
     * @return The names of all items in inventory.
     */
    private String[] getItemList() {
        String[] arrayItems = new String[]{};
        if (this.bllInventory.load()) {
            ArrayList<AnInventoryItem> listInventory = this.bllInventory.getList();
            ArrayList<String> aList = new ArrayList<>();
            listInventory.forEach((anItem) -> {
                inventory.put(anItem.getDescription(), anItem);
                aList.add(anItem.getDescription());
            });
            return aList.toArray(arrayItems);
        } else {
            JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
            return arrayItems;
        }
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
            JDialog frame = OrderItemDetailsDialog.this;
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

    private class ExtendedPriceUpdater implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
        }

        @Override
        public void focusLost(FocusEvent e) {
            Integer quantity = Integer.parseInt(tfQuantityOrdered.getText());
            Double price = Double.parseDouble(tfPrice.getText());
            Double extendedPrice = quantity * price;
            tfExtPrice.setText(extendedPrice.toString());
        }

    }
    // </editor-fold>
}
