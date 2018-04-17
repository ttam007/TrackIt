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

    private final AnInventoryItem anItem = null;

    //private final InventoryItem testItem = new InventoryItem();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlMain;
    JButton btnOK, btnCancel;
    JRadioButton inButton, outButton;
    JComboBox<String> itemComboBox;
    JLabel itemNameLabel, qtyLabel;
    JTextField qtyTextField;
    String[] itemStrings = {"soap", "shampoo", "conditioner", "paper towels", "mouthwash"};

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Check In/Out UI
     */
    public CheckInOutDialog() {
        initializeComponents();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

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
        this.getRootPane().setDefaultButton(btnOK);

        //Add all components here and set properties.
        Box buttonBx, itemBx, qtyBx, submitBx, combine;

        pnlMain = new JPanel();
        add(pnlMain, BorderLayout.CENTER);

        //create the Radio Buttons and add them to a group
        buttonBx = Box.createHorizontalBox();
        ButtonGroup checkGroup = new ButtonGroup();
        inButton = new JRadioButton("Check In");
        outButton = new JRadioButton("Check Out");
        checkGroup.add(inButton);
        checkGroup.add(outButton);
        inButton.setSelected(true);
        buttonBx.add(inButton);
        buttonBx.add(outButton);

        itemBx = Box.createHorizontalBox();
        itemNameLabel = new JLabel("Item Name");
        itemBx.add(itemNameLabel);
        /**
         * corrected to address compile warning
         */
        itemComboBox = new JComboBox<>(itemStrings);
        itemBx.add(itemComboBox);
        qtyBx = Box.createHorizontalBox();
        qtyLabel = new JLabel("Quantity");
        qtyBx.add(qtyLabel);
        qtyTextField = new JTextField();
        qtyBx.add(qtyTextField);
        submitBx = Box.createHorizontalBox();

        btnOK = new JButton("OK");
        submitBx.add(btnOK);
        this.btnOK.addActionListener((ActionEvent e) -> {
            saveAction();
        });

        btnCancel = new JButton("Cancel");
        submitBx.add(btnCancel);
        this.btnCancel.addActionListener((ActionEvent e) -> {
            cancelAction();
        });

        //add all of the boxes together
        combine = Box.createVerticalBox();
        combine.add(buttonBx);
        combine.add(itemBx);
        combine.add(qtyBx);
        combine.add(submitBx);

        pnlMain.add(combine);
        //Finalizations
        pack();

    }

    /**
     * Handles the save action. If any errors, then display error message
     * instead.
     *
     */
    private void saveAction() {
        JOptionPane.showMessageDialog(null, "Successfully Updated");
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
        JOptionPane.showMessageDialog(null, "Changed Cancelled");
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
