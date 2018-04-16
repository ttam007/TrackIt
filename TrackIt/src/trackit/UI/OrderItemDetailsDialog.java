package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import trackit.*;
import trackit.DAL.AnOrderItem;

/**
 * UI Layer: Handles all aspects of the Add Item to Order and Edit Order Item
 * dialog.
 */
public class OrderItemDetailsDialog
        extends ItemDetailsDialog<AnOrderItem> {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final boolean isCreateMode;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlCenter;
    JLabel lblName, lblQuantity, lblPrice, lblStatus, lblExtPrice;
    JTextField tfName, tfQuantity, tfPrice, tfStatus, tfExtPrice;
    JButton btnOK, btnCancel;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     *
     * @param useCreateMode
     */
    public OrderItemDetailsDialog(boolean useCreateMode) {
        super("Order Item Details", new AnOrderItem());

        this.isCreateMode = useCreateMode;
        this.initializeComponents();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
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
        //this.addWindowListener(new SupplierDetailsUI.CloseQuery());
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
            this.dispose();
        });

        btnCancel = new JButton("Cancel");
        submitBx.add(btnCancel);

        this.btnCancel.addActionListener((ActionEvent e) -> {
            //TODO:  close window and return to prior window.
            this.dispose();
        });

        combine = Box.createVerticalBox();
        combine.add(nameBx);
        combine.add(priceBx);
        combine.add(statusBx);
        combine.add(submitBx);

        pnlCenter.add(combine);

        super.finalizeComponents();
    }

    /**
     *
     */
    @Override
    protected void actionSave() {
        /*if (!bal.save()) {
            //TODO:  display bal.getErrorMessage();
        }*/
    }

    /**
     *
     */
    @Override
    protected void actionCancel() {
        //TODO:  close window and return to prior window.
    }

    /**
     *
     */
    @Override
    protected void actionDelete() {
        /*if (bal.remove()) {
            //TODO:  close window and return to prior window.
        } else {
            //TODO:  display bal.getErrorMessage() and stay on this window.
        }*/
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    // </editor-fold>
}
