package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Inventory bllInventory = new Inventory();
    private DialogResultType dialogResult = DialogResultType.NONE;

    //private final InventoryItem testItem = new InventoryItem();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlMain;
    JButton btnOK, btnCancel;
    JRadioButton inButton, outButton;
    private JComboBox<AnInventoryItem> cboItems;
    JLabel itemNameLabel, qtyLabel;
    JTextField qtyTextField;
    String[] itemStrings = {"soap", "shampoo", "conditioner", "paper towels", "mouthwash"};
    GridBagConstraints gbc;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Check In/Out UI
     */
    public CheckInOutDialog(AnInventoryItem anInventoryItem) {
        this.anInventoryItem = anInventoryItem;
        initializeComponents();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

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
        cboItems = new JComboBox<>(getItemList());
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        add(cboItems, gbc);
        cboItems.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                getItemList();
                validate();
            }
        });


        // Website Address label
        qtyLabel = new JLabel("Quantity: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(qtyLabel, gbc);

        //Website Address Text Field
        qtyTextField = new JTextField(7);
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
        if (checkInObject()) {
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
    
    private boolean checkInObject() {
        boolean returnValue = false;
        if (inButton.isSelected()){
            try {
                int oldQuant = this.anInventoryItem.getQuantity();
                int checkQuant = Utilities.parseFormattedInteger(this.qtyTextField.getText());
                this.anInventoryItem.setQuantity(oldQuant + checkQuant);
                returnValue = true;
            } catch (SQLException ex) {
                Logger.getLogger(CheckInOutDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (outButton.isSelected()) {
            try {
                int oldQuant = this.anInventoryItem.getQuantity();
                int checkQuant = Utilities.parseFormattedInteger(this.qtyTextField.getText());
                this.anInventoryItem.setQuantity(oldQuant - checkQuant);
                returnValue = true;
            } catch (SQLException ex) {
                Logger.getLogger(CheckInOutDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
            
        return returnValue;
    }
    
    private AnInventoryItem[] getItemList() {
        AnInventoryItem[] arrayItems = new AnInventoryItem[]{};
        
        ArrayList<AnInventoryItem> listInventoryItems = new ArrayList<>();

        if (bllInventory.load()) {
            listInventoryItems = bllInventory.getList();
        } else {
            JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        }
        return listInventoryItems.toArray(arrayItems);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    /**
     * Displays the frame.
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
