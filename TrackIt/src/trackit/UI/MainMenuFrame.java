package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Main Menu's UI.
 *
 * @author Douglas
 */
public class MainMenuFrame
        extends JFrame {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    /**
     * The name of the window.
     */
    private static final String WINDOW_NAME = "Main Menu";
    private final MainMenu bll;

    SuppliersPanel suppliersTab = new SuppliersPanel();
    DashboardPanel dashboardTab = new DashboardPanel();
    OrdersPanel ordersTab = new OrdersPanel();
    InventoryItemsPanel inventoryTab = new InventoryItemsPanel();
    JTabbedPane tabpane;
    JLabel title;
    JButton btnLogout, btnExit;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Main menu
     */
    public MainMenuFrame() {
        this.bll = new MainMenu();
        initializeComponents();

        refreshDashBoards();
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

    /**
     * Sets up all components used in this frame.
     */
    private void initializeComponents() {
        //Setup main frame
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setSize(1280, 786);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());
        setVisible(true);
        setLayout(new BorderLayout());

        //Add all components here and set properties.
        tabpane = new JTabbedPane();
        tabpane.add(DashboardPanel.TAB_NAME, dashboardTab);
        tabpane.add(InventoryItemsPanel.TAB_NAME, inventoryTab);
        tabpane.add(OrdersPanel.TAB_NAME, ordersTab);
        tabpane.add(SuppliersPanel.TAB_NAME, suppliersTab);

        add(tabpane, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel();
        btnLogout = new JButton("Log Out");
        btnLogout.addActionListener((ActionEvent e) -> {
            setVisible(false);
            LoginFrame login = new LoginFrame();
            login.display();
        });
        btnExit = new JButton("Exit");
        btnExit.addActionListener((ActionEvent e) -> {
            CloseQuery qry = new CloseQuery();
            qry.windowClosing(null);
        });
        pnlBottom.add(btnLogout);
        pnlBottom.add(btnExit);
        add(pnlBottom, BorderLayout.SOUTH);

        //Finalizations
        //pack();
    }

    /**
     * Refreshes the dashboards with current data.
     */
    private void refreshDashBoards() {
        ArrayList<Dashboard> dashboards = bll.getDashboards();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    /**
     * Displays the frame.
     */
    public void display() {
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
            JFrame frame = MainMenuFrame.this;
            int result = JOptionPane.showConfirmDialog(frame,
                    "Are you done with this program?", "Exit Program",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                System.exit(0);
            }
        }
    }
    // </editor-fold>
}
