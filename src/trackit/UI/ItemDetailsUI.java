package trackit.UI;

import trackit.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * UI layer that handles all things related to items.
 */
public class ItemDetailsUI extends JFrame {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    private static final String WINDOW_NAME = "Items";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlMain = new JPanel();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public ItemDetailsUI() {
        initializeComponents();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    private void initializeComponents() {
        final int frameWidth = 1200;
        final int frameHeight = 600;
        final Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.add(this.pnlMain);
        this.pack();
    }

    public void display() {
        System.out.println(String.format("Displaying {0}...", WINDOW_NAME));
        this.setVisible(true);
    }
// </editor-fold>
}
