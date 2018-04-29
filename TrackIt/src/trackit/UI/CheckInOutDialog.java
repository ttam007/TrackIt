package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Check In/Out dialog.
 *
 * @author Steven, Bond
 */
public class CheckInOutDialog
        extends JDialog {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    /**
     * The name of the dialog.
     */
    public static final String WINDOW_NAME = "Check In/Out Item";
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">

    private final AnInventoryItem anInventoryItem;
    private final Inventory bllInventory = new Inventory();
    private DialogResultType dialogResult = DialogResultType.NONE;
    private final static String CHECKOUT_MSG = "Check out quantity must be less than total quantity.";

    //private final InventoryItem testItem = new InventoryItem();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlMain;
    JButton btnOK, btnCancel;
    JRadioButton inButton, outButton;
    JLabel itemNameLabel, qtyLabel;
    JTextField itemTextField;
    JFormattedTextField qtyTextField;
    GridBagConstraints gbc;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Check In/Out UI
     *
     * @param anInventoryItem The item to be checked in/out.
     */
    public CheckInOutDialog(AnInventoryItem anInventoryItem) {
        this.anInventoryItem = anInventoryItem;
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
        int frameHeight = 250;//originally 110
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setSize(dimFrame);
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

        ButtonGroup checkGroup = new ButtonGroup();
        inButton = new JRadioButton("Check In");
        outButton = new JRadioButton("Check Out");
        checkGroup.add(inButton);
        checkGroup.add(outButton);
        inButton.setSelected(true);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(inButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(outButton, gbc);

        // Supplier Name Label 
        itemNameLabel = new JLabel("Item Name: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(itemNameLabel, gbc);

        // Supplier Name Text Field
        itemTextField = new JTextField();
        itemTextField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        add(itemTextField, gbc);

        // Website Address label
        qtyLabel = new JLabel("Quantity: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(qtyLabel, gbc);

        //Website Address Text Field
        qtyTextField = new JFormattedTextField(Utilities.getIntegerFormatter());
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        add(qtyTextField, gbc);

        // Init Ok Button
        btnOK = new JButton(Utilities.BUTTON_OK);
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(btnOK, gbc);
        btnOK.addActionListener((ActionEvent e) -> {
            saveAction();
        });

        //Cancel
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
     * Handles the save action. If any errors, then display error message
     * instead.
     *
     */
    private void saveAction() {
        if (checkInItem()) {
            if (this.bllInventory.save(this.anInventoryItem)) {
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
     * Handles the cancel action. If any errors, then display error message
     * instead.
     *
     */
    private void cancelAction() {
        JOptionPane.showMessageDialog(null, "Change Cancelled");
        //TODO:  close window and return to prior window.
        this.dispose();
    }

    private void populateComponents() {
        this.itemTextField.setText(this.anInventoryItem.getDescription());
    }

    private boolean checkInItem() {
        boolean returnValue = false;

        try {
            if (inButton.isSelected()) {
                this.anInventoryItem.changeQuantity((Integer) this.qtyTextField.getValue());
            } else if (outButton.isSelected()) {
                this.anInventoryItem.changeQuantity(-((Integer) this.qtyTextField.getValue()));
            }
            returnValue = true;
        } catch (NegativeAmountException ex) {
            JOptionPane.showMessageDialog(this, CHECKOUT_MSG,
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.INFORMATION_MESSAGE);
        }

        return returnValue;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Displays the frame.
     *
     * @return dialogResult
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
            JDialog frame = CheckInOutDialog.this;
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
