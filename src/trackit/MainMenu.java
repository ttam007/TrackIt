package trackit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * BAL Layer that handles all things related to the Main Menu.
 */
public class MainMenu extends JFrame {

    JPanel pnlMain = new JPanel();

    public MainMenu() {
        initializeComponents();
    }

    private void initializeComponents() {
        final int frameWidth = 1200;
        final int frameHeight = 600;
        final Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle("TrackIt - Main Menu");
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());
        this.add(this.pnlMain);
        this.pack();
    }

    public void display() {
        System.out.println("Displaying ...");
        this.setVisible(true);
    }

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
}
