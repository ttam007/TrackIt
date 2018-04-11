package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Suppliers panel. 
 */
public class SuppliersUI extends JPanel{
    // <editor-fold defaultstate="collapsed" desc="Constants">

    private static final String WINDOW_NAME = "Suppliers";
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final ArrayList<Supplier> suppliers = new ArrayList<>();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JButton btnCreate, btnRemove, btnEdit;
    String[] suppliersLabel = {"Supplier", "Web Address"};
    JTable suppliersTable;
    SupplierDetailsUI details;
    int selectedRow;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public SuppliersUI() {
        setLayout(new BorderLayout());
        
        //add data to suppliers arraylist 
        Object[][] suppliersTestData = {{"Amazon", "http://www.amazon.com"}, {"Walmart", "http://www.walmart.com"}, {"Ebay", "http://www.ebay.com"} };
        suppliersTable = new JTable(suppliersTestData, suppliersLabel);
        JScrollPane suppliersScrollPane = new JScrollPane(suppliersTable);
        suppliersTable.setFillsViewportHeight(true);
        suppliersTable.setDefaultEditor(Object.class, null);
        
        add(suppliersScrollPane, BorderLayout.CENTER);
        
        JPanel btmSup = new JPanel();
        
        btnCreate = new JButton("Create");
        btnCreate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("create supply");
                details = new SupplierDetailsUI(true);
            }
        });
        
        btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("Edit supply");
                //if list item selected edit item else select item
                selectedRow = suppliersTable.getSelectedRow();
                if(selectedRow < 0){
                    JOptionPane.showMessageDialog(null, "Select item to edit");
                }else{
                    details = new SupplierDetailsUI(false);
                    //TODO: enter item info of selected item
                }
                
            }         
        });
        
        btnRemove = new JButton("Remove");
        btnRemove.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("remove supply");
                selectedRow = suppliersTable.getSelectedRow();
                if(selectedRow < 0){
                    JOptionPane.showMessageDialog(null, "Select item to remove");
                }else{
                    //TODO: remove item from db
                    JOptionPane.showMessageDialog(null, "Item removed");
                }
            }
        });
        
        btmSup.add(btnCreate);
        btmSup.add(btnEdit);
        btmSup.add(btnRemove);
        
        add(btmSup, BorderLayout.SOUTH);
        
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    /**
     * Displays the frame.
     */
    public void display() {
        System.out.println(String.format("Displaying {0}...", WINDOW_NAME));
        setVisible(true);
    }

    // </editor-fold>
}
