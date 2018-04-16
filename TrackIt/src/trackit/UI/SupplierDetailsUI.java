package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import trackit.*;
import trackit.DAL.ASupplier;

/**
 * UI Layer: Handles all aspects of the Create ASupplier and Edit ASupplier
 * dialog.
 *
 * @author Douglas
 */
public class SupplierDetailsUI
        extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    private static final String WINDOW_NAME = "Supplier Details";
    private final ASupplier bll;
    private final boolean isCreateMode;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlCenter;
    JLabel lblName, lblAddress;
    JTextField tfName, tfAddress;
    JButton btnOK, btnCancel;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     *
     * @param useCreateMode
     */
    public SupplierDetailsUI(boolean useCreateMode) {
        this.bll = new ASupplier();
        this.isCreateMode = useCreateMode;
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
        int frameHeight = 110;
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
        Box nameBx, addressBx, submitBx, combine;

        pnlCenter = new JPanel();
        add(pnlCenter, BorderLayout.CENTER);

        nameBx = Box.createHorizontalBox();
        lblName = new JLabel("Supplier Name:   ");
        nameBx.add(lblName);
        tfName = new JTextField(20);
        nameBx.add(tfName);
        addressBx = Box.createHorizontalBox();
        lblAddress = new JLabel("Website Address:");
        addressBx.add(lblAddress);
        tfAddress = new JTextField(20);
        addressBx.add(tfAddress);
        submitBx = Box.createHorizontalBox();
        btnOK = new JButton("OK");
        submitBx.add(btnOK);

        this.btnOK.addActionListener((ActionEvent e) -> {
            this.dispose();

            /* if (!bll.save()) {
                //TODO:  display bal.getErrorMessage();

            }*/
        });

        btnCancel = new JButton("Cancel");
        submitBx.add(btnCancel);

        this.btnCancel.addActionListener((ActionEvent e) -> {
            this.dispose();
            //TODO:  close window and return to prior window.
        });
        combine = Box.createVerticalBox();
        combine.add(nameBx);
        combine.add(addressBx);
        combine.add(submitBx);

        pnlCenter.add(combine);

        //Finalizations
        pack();
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

    /**
     * close window
     */
    public void closeWindow() {
        this.setVisible(false);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SubClasses">
    /**
     * Handles all aspects of closing the program.
     */
    private class CloseQuery extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            JFrame frame = (JFrame) e.getSource();
            int result = JOptionPane.showConfirmDialog(frame,
                    "Do you want to save?", "Close Query",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                //TODO
                JOptionPane.showMessageDialog(null, "Successfully Updated");
                closeWindow();
            } else {
                //TODO
                closeWindow();
            }
        }
    }
    // </editor-fold>
}
