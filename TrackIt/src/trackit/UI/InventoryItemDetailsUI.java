package trackit.UI;



import trackit.DAL.AnInventoryItem;

import javax.swing.*;
import java.awt.*;

//getWindowCaption
//MainMenuUI is using this.setTitle(WINDOW_NAME);
//  now instead of the original code
//This is the correct code: this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
/**
 * UI Layer: Handles all aspects of the Create Inventory Item and Edit Inventory
 * Item dialog.
 */

public class InventoryItemDetailsUI extends JPanel {

    private final boolean isCreateMode;
    private JFrame mainFrame;
    private JTextField skuField, quantityField,expDateField,unitField,statusField,itemNameField ;
    private JLabel sku,statusLabel,unit,quantity,expDate, itemNameLabel;
    private JButton okInventoryItem,cancelInventoryItem;
    private GridBagConstraints gbc;


public class InventoryItemDetailsUI
        extends ItemDetailsUI<AnInventoryItem> {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
      private final boolean isCreateMode;
   // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     *
     * @param useCreateMode
     */

    public InventoryItemDetailsUI(boolean useCreateMode) {
       // super("Inventory Item Details", new AnInventoryItem());
        this.isCreateMode = useCreateMode;
        this.mainFrame = new JFrame();
        this.initializeComponents();



    }

     /**
     * Sets up all components used in this frame.
     */
   protected void initializeComponents() {
       gbc = new GridBagConstraints();
       setLayout(new GridBagLayout());
       gbc.insets = new Insets(2,2,10,0);
       gbc.anchor = GridBagConstraints.LINE_START;
       gbc.fill = GridBagConstraints.HORIZONTAL;

       // Item Name Label Initialized
       itemNameLabel = new JLabel("Item Name: ");
       gbc.gridx =0;
       gbc.gridy =0;
       add(itemNameLabel,gbc);

       // Item Name Text Field
       itemNameField = new JTextField(25);
       itemNameField.setEditable(this.isCreateMode);
       gbc.gridx=1;
       gbc.gridy=0;
       gbc.gridwidth=5;
       add(itemNameField,gbc);


       // Initialize Sku label and text field
       sku = new JLabel("SKU: ");
       gbc.gridx =0;
       gbc.gridy =1;
       gbc.gridwidth=1;
       add(sku,gbc);
       skuField = new JTextField(25);
       skuField.setEditable(this.isCreateMode);
       gbc.gridx =1;
       gbc.gridy =1;
       gbc.gridwidth=5;
       add(skuField,gbc);
       // Init Quantity
       //Label
       quantity = new JLabel("Quantity: ");
       gbc.gridx =0;
       gbc.gridy =2;
       gbc.gridwidth=1;
       add(quantity,gbc);
       // Field
       quantityField = new JTextField(7);
       quantityField.setEditable(this.isCreateMode);
       gbc.gridx =1;
       gbc.gridy =2;
       gbc.gridwidth=3;
       add(quantityField,gbc);
       // Init Exp Date Label and Field
       expDate = new JLabel("Exp Date: ");
       gbc.gridx =4;
       gbc.gridy =2;
       gbc.gridwidth=1;
       add(expDate,gbc);
       expDateField = new JTextField(7);

       gbc.gridx =5;
       gbc.gridy =2;
       gbc.gridwidth=1;
       add(expDateField,gbc);
       // Unit
       //Label
       unit = new JLabel("Unit: ");
       gbc.gridx =0;
       gbc.gridy =3;
       gbc.gridwidth=1;
       add(unit,gbc);
       // Field
       unitField = new JTextField(7);
       unitField.setEditable(this.isCreateMode);
       gbc.gridx =1;
       gbc.gridy =3;
       gbc.gridwidth=3;
       add(unitField,gbc);

       // Init Status Label and Field
       statusLabel = new JLabel("Status: ");
       gbc.gridx =4;
       gbc.gridy =3;
       gbc.gridwidth=1;
       add(statusLabel,gbc);
       //Text Field
       statusField = new JTextField(7);


       gbc.gridx =5;
       gbc.gridy =3;
       gbc.gridwidth=1;
       add(statusField,gbc);

       // Init Ok Button
       okInventoryItem = new JButton("Ok");
       gbc.gridx = 3;
       gbc.gridy = 4;
       gbc.gridwidth = 1;
       add(okInventoryItem,gbc);

       //Cancel
       cancelInventoryItem = new JButton("Cancel");
       gbc.gridx = 4;
       gbc.gridy = 4;
       gbc.gridwidth =1;
       add(cancelInventoryItem,gbc);

       mainFrame.add(this);
       mainFrame.setTitle("Inventory Items Detail");
       mainFrame.pack();
       mainFrame.setVisible(true);



    }
    /*

    /**
     *
     */

    @Override
    protected void actionSave() {
        /*if (!bal.save()) {
            //TODO:  display bal.getErrorMessage();

        }
    }*/
    /*

        }*/
    }

    /**
     *
     */

    @Override
    protected void actionCancel() {
        //TODO:  close window and return to prior window.
    }

    /**
     *
     */
    @Override
    protected void actionDelete() {
        /*if (bal.remove()) {
            //TODO:  close window and return to prior window.
        } else {
            //TODO:  display bal.getErrorMessage() and stay on this window.
        }
    }*/
    

}
