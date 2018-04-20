package trackit.UI;

import trackit.Dashboard;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.*;

/**
 * UI Layer: Handles all aspects of the Dashboard panel.
 *
 * @author Bond, Diaz
 */
public class DashboardPanel
        extends JPanel {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    /**
     * The name of the panel.
     */
    public static final String TAB_NAME = "Dashboard";
    private ArrayList<Dashboard> dashboards = new ArrayList<Dashboard>();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">

    private JTextArea dashboardInfo;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates new form DashboardUI
     */
    public DashboardPanel() {
        initializeComponents();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    private void initializeComponents() {

        //String sb = "- 5 items are out of stock\n- Milk will expire in 3 days\n- Order arriving today\n";
        StringBuilder sb = new StringBuilder();

        for (Dashboard d : dashboards) {
            sb.append(d.toString() + "\n");
        }
        dashboardInfo = new JTextArea(35, 90);
        dashboardInfo.setText(sb.toString());
        dashboardInfo.setEditable(false);

        JScrollPane sp = new JScrollPane(dashboardInfo);
        sp.setSize(new Dimension(1000, 400));
        add(sp);
    }
    // </editor-fold>
}
