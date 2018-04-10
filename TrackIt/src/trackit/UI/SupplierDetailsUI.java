package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Create Supplier and Edit Supplier
 * dialog.
 */
public class SupplierDetailsUI
        extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    private static final String WINDOW_NAME = "Supplier Details";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final Supplier bll = new Supplier();
    private final boolean isCreateMode;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlMain = new JPanel();
    JButton btnOK = new JButton();
    JButton btnCancel = new JButton();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SupplierDetailsUI(boolean useCreateMode) {
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
        int frameWidth = 600;
        int frameHeight = 400;
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());
        this.setVisible(true);

        //Add all components here and set properties.
        this.add(pnlMain);

        this.add(this.btnOK);
        this.btnOK.addActionListener((ActionEvent e) -> {
            if (!bll.save()) {
                //TODO:  display bal.getErrorMessage();
            }
        });
        this.add(this.btnCancel);
        this.btnCancel.addActionListener((ActionEvent e) -> {
            //TODO:  close window and return to prior window.
        });

        


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
    public void closeWindow(){
        //this.closeWindow();
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
                //closeWindow();
            } else {
                //TODO
                //closeWindow();
            }
        }
    }
    // </editor-fold>
}
