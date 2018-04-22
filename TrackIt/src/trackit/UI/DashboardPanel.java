package trackit.UI;

import java.awt.Dimension;
import javax.swing.*;

/**
 * UI Layer: Handles all aspects of the Dashboard panel.
 *
 * @author Bond
 */
public class DashboardPanel
        extends JPanel {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    /**
     * The name of the panel.
     */
    public static final String TAB_NAME = "Dashboard";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">

    private JTextArea dashboardInfo;
    private JFrame mainFrame;
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

        String sb = "- 5 items are out of stock\n- Milk will expire in 3 days\n- Order arriving today\n";

        dashboardInfo = new JTextArea(35, 90);
        dashboardInfo.setText(sb);
        dashboardInfo.setEditable(false);

        JScrollPane sp = new JScrollPane(dashboardInfo);
        sp.setSize(new Dimension(1000, 400));
        add(sp);
    }
    // </editor-fold>
}
