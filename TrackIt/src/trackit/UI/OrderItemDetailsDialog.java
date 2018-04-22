package trackit.UI;

import java.awt.*;
import java.awt.event.*;
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

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlCenter;
    JLabel lblName, lblQuantity, lblPrice, lblStatus, lblExtPrice;
    JTextField tfName, tfQuantity, tfPrice, tfStatus, tfExtPrice;
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

        //TODO:  add additional components here.
        int frameWidth = 500; //originally 660
        int frameHeight = 250; //originally 150
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());
        this.getRootPane().setDefaultButton(btnOK);
        this.setModal(true);

        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        gbc.insets = new Insets(2, 2, 5, 0);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Item Name Label Initialized
        lblName = new JLabel("Item Name: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblName, gbc);

        // Item Name Text Field
        tfName = new JTextField(25);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        add(tfName, gbc);

        // Initialize Sku label and text field
        lblQuantity = new JLabel("Quantity: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(lblQuantity, gbc);

        tfQuantity = new JTextField(7);
        tfQuantity.setEditable(this.isCreateMode);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        add(tfQuantity, gbc);
        // Init Quantity
        //Label
        lblPrice = new JLabel("Price: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(lblPrice, gbc);

        // Field
        tfPrice = new JTextField(7);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(tfPrice, gbc);

        // Init Exp Date Label and Field
        lblStatus = new JLabel("Status: ");
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(lblStatus, gbc);

        tfStatus = new JTextField(10);
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(tfStatus, gbc);
        // Unit
        //Label
        lblExtPrice = new JLabel("Ext Price: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(lblExtPrice, gbc);
        // Field
        tfExtPrice = new JTextField(7);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        add(tfExtPrice, gbc);

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

        //Finalizations
        pack();
    }

    /**
     * Populates all the UI components from the object in memory.
     */
    private void populateComponents() {
        this.tfName.setText(this.anOrderItem.getDescription());
        this.tfStatus.setText(this.anOrderItem.getItemStatus().getText());
        this.tfQuantity.setText(this.anOrderItem.getQuantityOrdered().toString());
        this.tfPrice.setText(this.anOrderItem.getPrice().toString());
    }

    /**
     * Populates the object in memory from all the UI components.
     */
    private boolean populateObject() {
        boolean returnValue = false;
        //TODO:  sort this out so boolean return is used instead of try/catch block.
        try {
            this.anOrderItem.setDescription(this.tfName.getText());
            this.anOrderItem.setQuantityOrdered(Integer.parseInt(this.tfQuantity.getText()));
            this.anOrderItem.setPrice(Double.parseDouble(this.tfPrice.getText()));
            returnValue = true;
        } catch (java.sql.SQLException exSQL) {
            JOptionPane.showMessageDialog(this, this.anOrderItem.getErrorMessage(),
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
                JOptionPane.showMessageDialog(this, this.bll.getErrorMessage(),
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
    // </editor-fold>
}
