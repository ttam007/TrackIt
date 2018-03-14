package trackit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Handles all aspects of the Main Menu UI.
 */
public class MainMenu extends JFrame {

    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlMain = new JPanel();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public MainMenu() {
        initializeComponents();
    }
    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Private Methods">
    /**
     * Initializes the GUI.
     */
    private void initializeComponents() {
        final int frameWidth = 1200;
        final int frameHeight = 600;
        final Dimension dimFrame = new Dimension(frameWidth, frameHeight);

        //JFrame
        this.setTitle("TrackIt - Main Menu");
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());

        /* Add additional components here.  After each component is added, 
        configure it before moving on to the next one.        
         */
        this.add(this.pnlMain);

        //Finishing touches
        this.pack();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Display the UI with results of analysis.
     */
    public void display() {
        System.out.println("Displaying ...");
        this.setVisible(true);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Sub-Classes">
    /**
     * Handles all actions necessary when the User attempts to close the
     * program.
     */
    private class CloseQuery extends WindowAdapter {

        //WindowAdapter methods
        @Override
        public void windowClosing(WindowEvent e) {
            JFrame frame = (JFrame) e.getSource();

            int result = JOptionPane.showConfirmDialog(frame,
                    "Are you done with this program?",
                    "Exit Program",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        }
    }
    // </editor-fold>
}
