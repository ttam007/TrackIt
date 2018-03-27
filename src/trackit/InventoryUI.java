package trackit;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * UI layer that handles all things related to the inventory.
 */
public class InventoryUI extends JFrame {

    JPanel pnlMain = new JPanel();

    public InventoryUI() {
        initializeComponents();
    }

    private void initializeComponents() {
        final int frameWidth = 1200;
        final int frameHeight = 600;
        final Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle("TrackIt - Inventory");
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.add(this.pnlMain);
        this.pack();
    }

    public void display() {
        System.out.println("Displaying ...");
        this.setVisible(true);
    }
}
