package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Main Menu's UI.
 */
public class MainMenuUI extends JFrame {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    private static final String WINDOW_NAME = "Main Menu";
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final MainMenu bal = new MainMenu();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    //JPanel pnlMain = new JPanel();
    //InventoryItemsUI inventoryTab = new InventoryItemsUI();
    //OrdersUI ordersTab = new OrdersUI();
    SupplierDetailsUI suppliersTab = new SupplierDetailsUI();
    
    JPanel dashboard, inventory, supplies, orders, incomingPurchases, inventoryStats, dashbox4, notifications;
    JTabbedPane tabpane;
    JLabel title;
    JButton logout, exit, searchSupplier, addSupplier, removeSupplier, editSupplier, order;
    String[] suppliersLabel = {"Supplier", "Web Address"};
    JTable suppliersTable;
    
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
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle(WINDOW_NAME);
        this.setSize(screenSize.width, screenSize.height);
        //this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());
        setVisible(true);
        setLayout(new BorderLayout());

        //Add all components here and set properties.
        
        /****************DASHBOARD START****************/
        dashboard = new JPanel();
        dashboard.setLayout(new BorderLayout());
        
        JPanel center = new JPanel();
        JLabel test = new JLabel("words");
        center.add(test);
        dashboard.add(center, BorderLayout.CENTER);
        
        JPanel btm = new JPanel();
        logout = new JButton("Log Out");
        logout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("logout");
                setVisible(false);
                LoginUI login = new LoginUI();
                login.display();
            }
            
        });
        exit = new JButton("Exit");
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("exit");
                System.exit(0);
            }
            
        });
        btm.add(logout);
        btm.add(exit);
        dashboard.add(btm, BorderLayout.SOUTH);
        
        /****************DASHBOARD END****************/
        
        /****************INVENTORY START****************/
        inventory = new JPanel();
        
        
        /****************INVENTORY END****************/
        
        /****************ORDER START****************/
        orders = new JPanel();
        
        
        /****************ORDER END****************/
        
        /****************SUPPLIES START****************/
        supplies = new JPanel();
        supplies.setLayout(new BorderLayout());
        
        JPanel topSup = new JPanel();
        JLabel lblFilter = new JLabel("Filter");
        topSup.add(lblFilter);
        //JDropDownMenu ddFilter = new JDropDownMenu();
        
        Object[][] suppliersTestData = {{"Amazon", "http://www.amazon.com"}, {"Walmart", "http://www.walmart.com"}, {"Ebay", "http://www.ebay.com"} };
        suppliersTable = new JTable(suppliersTestData, suppliersLabel);
        JScrollPane suppliersScrollPane = new JScrollPane(suppliersTable);
        suppliersTable.setFillsViewportHeight(true);
        suppliersTable.setDefaultEditor(Object.class, null);
        
        supplies.add(suppliersScrollPane, BorderLayout.CENTER);
        
        JPanel btmSup = new JPanel();
        
        addSupplier = new JButton("Add");
        addSupplier.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("add supply");
                //AddSupplier add = new AddSupplier();
                
            }
            
        });
        
        editSupplier = new JButton("Edit");
        editSupplier.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("Edit supply");
                SupplierDetailsUI details = new SupplierDetailsUI();
            }
            
        });
        removeSupplier = new JButton("Remove");
        removeSupplier.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("remove supply");
                
            }
            
        });
        
        btmSup.add(addSupplier);
        btmSup.add(editSupplier);
        btmSup.add(removeSupplier);
        
        supplies.add(btmSup, BorderLayout.SOUTH);
        
        /****************SUPPLIES END****************/
        
        
        tabpane = new JTabbedPane();
        tabpane.add("Dashboard", dashboard);
        tabpane.add("Inventory", inventory);
        tabpane.add("Orders", orders);
        tabpane.add("Supplies", supplies);
        
        add(tabpane, BorderLayout.CENTER);
        
        //Finalizations
        //pack();
    }

    /**
     * Refreshes the dashboards with current data.
     */
    private void refreshDashBoards() {
        ArrayList<Dashboard> dashboards = bal.getDashboards();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    /**
     * Displays the frame.
     */
    public void display() {
        System.out.println(String.format("Displaying %s...", WINDOW_NAME));
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
