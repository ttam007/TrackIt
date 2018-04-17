package trackit.UI;

import javax.swing.*;
import java.awt.*;

//getWindowCaption
//MainMenuUI is using this.setTitle(WINDOW_NAME);
//  now instead of the original code
//This is the correct code: this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
/**
 * UI Layer: Handles all aspects of the Create Inventory Item and Edit Inventory
 * Item dialog.
 */
public class InventoryItemDetailsUI extends JFrame {

<<<<<<< HEAD
=======
    // <editor-fold defaultstate="collapsed" desc="Constants">
    /**
     * The name of the dialog.
     */
    public static final String WINDOW_NAME = "Inventory Item Details";
    // </editor-fold>
>>>>>>> origin/master
    private final boolean isCreateMode;
    //private final JFrame mainFrame;
    JPanel test;
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
        //this.mainFrame = new JFrame();
        initializeComponents();

    }

    /**
     *
     * @return
     */
    /*public JFrame getMainFrame() {
        return this.mainFrame;
    }
    */
    /**
     * Sets up all components used in this frame.
     */
    private void initializeComponents() {
        int frameWidth = 700;
        int frameHeight = 500;
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setPreferredSize(dimFrame);
        this.setResizable(false);
        this.setVisible(true);
        gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        gbc.insets = new Insets(2, 2, 10, 0);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //test = new JPanel();
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
        okInventoryItem.addActionListener((event) -> this.dispose());

        //Cancel
        cancelInventoryItem = new JButton("Cancel");
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(cancelInventoryItem, gbc);
        cancelInventoryItem.addActionListener((event) -> this.dispose());
        //Test

        //mainFrame.add(this);
        //add(test);
            //mainFrame.setTitle("Inventory Items Detail");
            //mainFrame.pack();
        //mainFrame.setVisible(true);
        pack();
    }

}
