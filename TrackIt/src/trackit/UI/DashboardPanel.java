package trackit.UI;

import java.awt.Dimension;
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
    private final Dashboard[] dashboards = {
        new Dashboard_CountOutOfStockItems(),
        new Dashboard_NextExpiredItem(),
        new Dashboard_NextOrderExpected(),
        new Dashboard_Last30DaysSpent()};

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
        dashboardInfo = new JTextArea(35, 90);
        dashboardInfo.setEditable(false);

        JScrollPane sp = new JScrollPane(dashboardInfo);
        sp.setSize(new Dimension(1000, 400));
        add(sp);
    }

    /**
     * Populates the Text Area.
     */
    private void initTextAreaData() {
        StringBuilder sb = new StringBuilder();
        for (Dashboard db : dashboards) {
            String newString = db.toString().trim();
            if (!sb.toString().equals("")
                    && !newString.equals("")) {
                newString = "\n" + newString;
            }
            sb.append(newString);
        }
        dashboardInfo.setText(sb.toString());
    }

    /**
     * Populates the dashboard information from the database.
     */
    private void populatesComponents() {
        try {
            initTextAreaData();
        } catch (Exception ex) {
            Utilities.setErrorMessage(ex);
            JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
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
