package trackit.UI;

import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.*;
import trackit.*;

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
    private final HashMap<Integer, Dashboard> dashboardHash = new HashMap<Integer, Dashboard>();
    private final Dashboard[] dashboards = {
        new Dashboard(DashboardType.COUNT_ITEMS_OUT_OF_STOCK),
        new Dashboard(DashboardType.DATE_NEXT_ITEM_EXPIRES),
        new Dashboard(DashboardType.DATE_NEXT_ORDER_ARRIVES),
        new Dashboard(DashboardType.MONEY_SPENT_LAST_30_DAYS)};

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
        populatesComponents();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Added solely to prevent serialization and Inspector items related to
     * such.
     *
     * @param stream
     * @throws java.io.IOException
     */
    private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
        throw new java.io.NotSerializableException(getClass().getName());
    }

    /**
     * Added solely to prevent serialization and Inspector items related to
     * such.
     *
     * @param stream
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     */
    private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException {
        throw new java.io.NotSerializableException(getClass().getName());
    }

    private void initializeComponents() {

        // String sb = "- 5 items are out of stock\n- Milk will expire in 3 days\n- Order arriving today\n";
        dashboardInfo = new JTextArea(35, 90);
        //dashboardInfo.setText(sb);
        dashboardInfo.setEditable(false);

        JScrollPane sp = new JScrollPane(dashboardInfo);
        sp.setSize(new Dimension(1000, 400));
        add(sp);
    }

    /**
     * populates the Text Area.
     *
     */
    private void initTextAreaData(Dashboard[] dashboards) {
        StringBuilder sb = new StringBuilder();
        for (Dashboard db : dashboards) {

            sb.append((db.toString().equals("") ? db.toString() : db.toString() + "\n"));
        }
        dashboardInfo.setText(sb.toString());
    }

    /**
     * populates the dashboard information from the DB
     *
     */

    private void populatesComponents() {
        boolean displayError = false;
        for (Dashboard db : dashboards) {
            if (!db.getData()) {
                displayError = true;
            }

        }

        if (displayError) {
            JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("Are we displaying");
            initTextAreaData(dashboards);
        }

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">
    /**
     * Refreshes all dashboard items on the panel.
     */
    public void refresh() {
        populatesComponents();

    }

    // </editor-fold>
}
