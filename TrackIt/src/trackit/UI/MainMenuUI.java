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
public class MainMenuUI extends JFrame {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    private static final String WINDOW_NAME = "Main Menu";
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final MainMenu bll = new MainMenu();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">

    SuppliersUI suppliersTab = new SuppliersUI();
    DashboardUI dashboardTab = new DashboardUI();
    OrdersUI ordersTab = new OrdersUI();
    InventoryItemsUI inventoryTab = new InventoryItemsUI();
    JTabbedPane tabpane;
    JLabel title;
    JButton btnLogout, btnExit;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public MainMenuUI() {
        initializeComponents();

        refreshDashBoards();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
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
        tabpane.add("Dashboard", dashboardTab);
        tabpane.add("Inventory", inventoryTab);
        tabpane.add("Orders", ordersTab);
        tabpane.add("Suppliers", suppliersTab);

        add(tabpane, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel();
        btnLogout = new JButton("Log Out");
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                LoginUI login = new LoginUI();
                login.display();
            }

        });
        btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

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
            JFrame frame = (JFrame) e.getSource();
            int result = JOptionPane.showConfirmDialog(frame,
                    "Are you done with this program?", "Exit Program",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        }
    }
    // </editor-fold>
}
