package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Supplier Details's UI.
 */
public class SupplierDetailsUI
        extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    private static final String WINDOW_NAME = "Supplier Details";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final Supplier bal = new Supplier();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlMain = new JPanel();
    JButton btnOK = new JButton();
    JButton btnCancel = new JButton();
    JButton btnDelete = new JButton();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SupplierDetailsUI() {
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
        

        //Finalizations
        pack();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    /**
     * Displays the frame.
     */
    public void display() {
        System.out.println(String.format("Displaying {0}...", WINDOW_NAME));
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
            JFrame frame = (JFrame) e.getSource();
            int result = JOptionPane.showConfirmDialog(frame,
                    "Do you want to save?", "Close Query",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                //TODO
                JOptionPane.showMessageDialog(null, "Successfully Updated");
                   
            } else {
                //TODO
            }
        }
    }
    // </editor-fold>
}
