package trackit.UI;


import java.awt.Dimension;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * UI Layer: Handles all aspects of the Dashboard panel.
 */
public class DashboardUI extends JPanel {
    private JTextArea dashboardInfo;
    private JFrame mainFrame;

    /**
     * Creates new form DashboardUI
     */
    public DashboardUI() {
        initializeComponents();
        this.setVisible(true);
    }

    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    private void initializeComponents() {

        StringBuilder sb = new StringBuilder("- 5 items are out of stock\n- Milk will expire in 3 days\n- Order arriving today\n");

        dashboardInfo = new JTextArea(35,90);
        dashboardInfo.setText(sb.toString());
        dashboardInfo.setEditable(false);

        JScrollPane sp = new JScrollPane(dashboardInfo);
        sp.setSize(new Dimension(1000,400));
        add(sp);
    }
    // </editor-fold>
}
