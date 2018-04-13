package trackit.UI;

import javax.swing.*;

/**
 * UI Layer: Handles all aspects of the Dashboard panel.
 */
public class DashboardUI extends JPanel {

    /**
     * Creates new form DashboardUI
     */
    public DashboardUI() {
        initializeComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    private void initializeComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
    }
    // </editor-fold>
}
