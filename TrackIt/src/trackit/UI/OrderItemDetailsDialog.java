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

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlCenter;
    JLabel lblName, lblQuantity, lblPrice, lblStatus, lblExtPrice;
    JTextField tfName, tfQuantity, tfPrice, tfStatus, tfExtPrice;
    JButton btnOK, btnCancel;

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
            this.anOrderItem = null;
        } else if (anOrderItem == null) {
            throw new IllegalArgumentException("When 'useCreateMode' = true, then a non-null anOrderItem must be provided.");
        } else {
            this.anOrderItem = anOrderItem;
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
     * initialize the items
     */
    private void initializeComponents() {

        //TODO:  add additional components here.
        int frameWidth = 660;
        int frameHeight = 150;
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());
        this.setVisible(true);
        this.getRootPane().setDefaultButton(btnOK);

        //Add all components here and set properties.
        Box nameBx, priceBx, statusBx, submitBx, combine;

        pnlCenter = new JPanel();
        add(pnlCenter, BorderLayout.CENTER);

        nameBx = Box.createHorizontalBox();
        lblName = new JLabel("Item Name:");
        nameBx.add(lblName);
        tfName = new JTextField(20);
        nameBx.add(tfName);

        priceBx = Box.createHorizontalBox();
        lblQuantity = new JLabel("    Quantity:");
        priceBx.add(lblQuantity);
        tfQuantity = new JTextField(20);
        priceBx.add(tfQuantity);
        lblPrice = new JLabel("         Price:");
        priceBx.add(lblPrice);
        tfPrice = new JTextField(20);
        priceBx.add(tfPrice);

        statusBx = Box.createHorizontalBox();
        lblStatus = new JLabel("        Status:");
        statusBx.add(lblStatus);
        tfStatus = new JTextField(20);
        statusBx.add(tfStatus);
        lblExtPrice = new JLabel("     Ext Price:");
        statusBx.add(lblExtPrice);
        tfExtPrice = new JTextField(20);
        statusBx.add(tfExtPrice);

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

        combine = Box.createVerticalBox();
        combine.add(nameBx);
        combine.add(priceBx);
        combine.add(statusBx);
        combine.add(submitBx);

        pnlCenter.add(combine);

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
