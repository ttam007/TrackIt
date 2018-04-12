package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Check In/Out dialog.
 * @author Steven
 */
public class CheckInOutUI
        extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    private static final String WINDOW_NAME = "Check In/Out";
// </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final InventoryItem testItem = new InventoryItem();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlMain;
    JButton btnOK, btnCancel;
    JRadioButton inButton, outButton;
    JComboBox<String> itemComboBox;
    JLabel itemNameLabel, qtyLabel;
    JTextField qtyTextField;
    String[] itemStrings = { "soap", "shampoo", "conditioner", "paper towels", "mouthwash" };

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public CheckInOutUI() {
    
        initializeComponents();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">

    /**
     * Sets up all components used in this frame.
     */
    private void initializeComponents() {
        //Setup main frame
        int frameWidth = 500;
        int frameHeight = 250;
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());
        this.setVisible(true);

        //Add all components here and set properties.
        Box buttonBx, itemBx, qtyBx, submitBx, combine;
        
        pnlMain = new JPanel();
        add(pnlMain, BorderLayout.CENTER);
        
        //create the Radio Buttons and add them to a group
        buttonBx = Box.createHorizontalBox();
        ButtonGroup checkGroup = new ButtonGroup();
        inButton = new JRadioButton("Check In");
        outButton = new JRadioButton("Check Out");
        checkGroup.add(inButton);
        checkGroup.add(outButton);
        inButton.setSelected(true);
        buttonBx.add(inButton);
        buttonBx.add(outButton);
        
        //item selection
        itemBx = Box.createHorizontalBox();
        itemNameLabel = new JLabel("Item Name");
        itemBx.add(itemNameLabel);
        itemComboBox = new JComboBox(itemStrings);
        itemBx.add(itemComboBox);
        qtyBx = Box.createHorizontalBox();
        qtyLabel = new JLabel("Quantity");
        qtyBx.add(qtyLabel);
        qtyTextField = new JTextField();
        qtyBx.add(qtyTextField);
        submitBx = Box.createHorizontalBox();
        btnOK = new JButton("OK");
        
        this.btnOK.addActionListener((ActionEvent e) -> {
            if(!testItem.save()) {
                
            }
        });
        
        btnCancel = new JButton("Cancel");
        submitBx.add(btnCancel);
        
        this.btnCancel.addActionListener((ActionEvent e) -> {
            //TODO:  close window and return to prior window.
        });
        
        //add all of the boxes together
        combine = Box.createVerticalBox();
        combine.add(buttonBx);
        combine.add(itemBx);
        combine.add(qtyBx);
        combine.add(submitBx);
        
        pnlMain.add(combine);
        //Finalizations
        pack();
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
    
    public void closeWindow() {
        this.setVisible(false);
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
                    "Do you want to save?", "Close Query",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                //TODO
                JOptionPane.showMessageDialog(null, "Successfully Updated");
                closeWindow();
            } else {
                //TODO
                closeWindow();
            }
        }
    }
    // </editor-fold>
}
