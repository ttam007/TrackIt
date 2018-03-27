package trackit.UI;

import trackit.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * BAL Layer that handles all things related to the Main Menu.
 */
public class MainMenuUI extends JFrame {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    private static final String WINDOW_NAME = "Main Menu";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final MainMenu balMain = new MainMenu();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlMain = new JPanel();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public MainMenuUI() {
        initializeComponents();

        refreshDashBoards();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

    private void initializeComponents() {
        final int frameWidth = 1200;
        final int frameHeight = 600;
        final Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());
        this.add(this.pnlMain);
        this.pack();
    }

    private void refreshDashBoards() {
        ArrayList<Dashboard> dashboards = balMain.getDashboards();
        //TODO:  populate UI with dashboard values.
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    public void display() {
        System.out.println(String.format("Displaying {0}...", WINDOW_NAME));
        this.setVisible(true);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Sub-Classes">

    private class CloseQuery extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            JFrame frame = (JFrame) e.getSource();
            int result = JOptionPane.showConfirmDialog(frame, "Are you done with this program?", "Exit Program", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        }
    }
// </editor-fold>
}
