package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Create Inventory Item and Edit Inventory
 * Item dialog.
 */
public class InventoryItemDetailsUI
        extends JDialog {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    public static final String WINDOW_NAME = "Inventory Item Details";
    // </editor-fold>
    private final boolean isCreateMode;
    private JTextField skuField, quantityField, expDateField, unitField, statusField, itemNameField;
    private JLabel sku, statusLabel, unit, quantity, expDate, itemNameLabel;
    private JButton okInventoryItem, cancelInventoryItem;
    private GridBagConstraints gbc;

    /**
     *
     * @param useCreateMode
     */
    public InventoryItemDetailsUI(boolean useCreateMode) {
        // super("Inventory Item Details", new AnInventoryItem());
        this.isCreateMode = useCreateMode;
        this.initializeComponents();

    }

    /**
     * Sets up all components used in this frame.
     */
    private void initializeComponents() {
        //Setup main frame
        int frameWidth = 640;
        int frameHeight = 400;
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setSize(dimFrame);
        this.setPreferredSize(dimFrame);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());

        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        gbc.insets = new Insets(2, 2, 10, 0);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Item Name Label Initialized
        itemNameLabel = new JLabel("Item Name: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(itemNameLabel, gbc);

        // Item Name Text Field
        itemNameField = new JTextField(25);
        itemNameField.setEditable(this.isCreateMode);
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
        skuField.setEditable(this.isCreateMode);
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
        expDate = new JLabel("Exp Date: ");
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(expDate, gbc);
        expDateField = new JTextField(7);

        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(expDateField, gbc);
        // Unit
        //Label
        unit = new JLabel("Unit: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(unit, gbc);
        // Field
        unitField = new JTextField(7);
        unitField.setEditable(this.isCreateMode);
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
        okInventoryItem = new JButton("Ok");
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(okInventoryItem, gbc);

        //Cancel
        cancelInventoryItem = new JButton("Cancel");
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(cancelInventoryItem, gbc);
        //Test

        this.pack();
    }

    /**
     * Displays the frame.
     */
    public void display() {
        System.out.println(String.format("Displaying %s...", WINDOW_NAME));
        setVisible(true);
    }

    // <editor-fold defaultstate="collapsed" desc="SubClasses">
    /**
     * Handles all aspects of closing the program.
     */
    private class CloseQuery extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            JDialog frame = InventoryItemDetailsUI.this;
            int result = JOptionPane.showConfirmDialog(frame,
                    "Do you want to save?", "Close Query",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                //TODO
                JOptionPane.showMessageDialog(null, "Successfully Updated");
                frame.dispose();
            } else {
                //TODO
                JOptionPane.showMessageDialog(null, "Changed Cancelled");
                frame.dispose();
            }
        }
    }
    // </editor-fold>

}
