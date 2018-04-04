package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the OrderItem's UI. TODO: convert to tab
 * panel
 */
public class OrdersUI
        extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    private static final String WINDOW_NAME = "Orders";
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final ArrayList<Order> orders = new ArrayList<>();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlMain = new JPanel();
    JButton btnAddToList = new JButton();
    JButton btnRemoveFromList = new JButton();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public OrdersUI() {
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
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());

        //Add all components here and set properties.
        this.add(pnlMain);
        this.add(this.btnAddToList);
        this.btnAddToList.addActionListener((ActionEvent e) -> {
            //TODO
        });
        this.add(this.btnRemoveFromList);
        this.btnRemoveFromList.addActionListener((ActionEvent e) -> {
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
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            } else {
                //TODO
            }
        }
    }
    // </editor-fold>
}
