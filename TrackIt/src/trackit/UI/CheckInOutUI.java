package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Check In/Out dialog.
 */
public class CheckInOutUI
        extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    private static final String WINDOW_NAME = "Check In/Out";
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlMain = new JPanel();
    JButton btnOK = new JButton();
    JButton btnCancel = new JButton();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public CheckInOutUI() {
        initializeComponents();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

    /**
     * Sets up all components used in this frame.
     */
    private void initializeComponents() {
        //Setup main frame
        int frameWidth = 1200;
        int frameHeight = 600;
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(0);

        //Add all components here and set properties.
        this.add(pnlMain);
        this.add(this.btnOK);
        this.btnOK.addActionListener((ActionEvent e) -> {
            //TODO
        });
        this.add(this.btnCancel);
        this.btnCancel.addActionListener((ActionEvent e) -> {
            //TODO
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
        System.out.println(String.format("Displaying {0}...", WINDOW_NAME));
        setVisible(true);
    }

    // </editor-fold>
}
