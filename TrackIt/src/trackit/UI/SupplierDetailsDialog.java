package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Create ASupplier and Edit ASupplier
 * dialog.
 *
 * @author Douglas, Steven
 */
public class SupplierDetailsDialog
        extends JDialog {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    /**
     * The name of the dialog.
     */
    public static final String WINDOW_NAME = "Supplier Details";
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    private final boolean isCreateMode;
    private final ASupplier aSupplier;
    private final Suppliers bll = new Suppliers();
    private DialogResultType dialogResult = DialogResultType.NONE;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlCenter;
    JLabel lblName, lblAddress;
    JTextField tfName, tfAddress;
    JButton btnOK, btnCancel;
    GridBagConstraints gbc;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Creates a new dialog for working with a single Supplier.
     *
     * @param useCreateMode True = use for a new Supplier; False = use to edit
     * the specified Supplier.
     * @param aSupplier The Supplier to be edited. This value is ignored if
     * useCreateMode is true.
     */
    public SupplierDetailsDialog(boolean useCreateMode, ASupplier aSupplier) {
        this.isCreateMode = useCreateMode;
        if (this.isCreateMode) {
            this.aSupplier = new ASupplier();
        } else if (aSupplier == null) {
            throw new IllegalArgumentException("When 'useCreateMode' = false, then a non-null aSupplier must be provided.");
        } else {
            this.aSupplier = aSupplier;
        }

        initializeComponents();
        populateComponents();
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
        int frameWidth = 500;
        int frameHeight = 200; //originally 110
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());
        this.getRootPane().setDefaultButton(btnOK);
        this.setModal(true);

        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        gbc.insets = new Insets(2, 2, 5, 0);
        gbc.anchor = GridBagConstraints.LINE_START;

        // Supplier Name Label 
        lblName = new JLabel("Supplier Name: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblName, gbc);

        // Supplier Name Text Field
        tfName = new JTextField(25);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        add(tfName, gbc);

        // Website Address label
        lblAddress = new JLabel("Website Address: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(lblAddress, gbc);

        //Website Address Text Field
        tfAddress = new JTextField(25);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        add(tfAddress, gbc);

        // Init Ok Button
        btnOK = new JButton(Utilities.BUTTON_OK);
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(btnOK, gbc);
        btnOK.addActionListener((ActionEvent e) -> {
            saveAction();
        });

        //Cancel
        btnCancel = new JButton(Utilities.BUTTON_CANCEL);
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(btnCancel, gbc);
        btnCancel.addActionListener((ActionEvent e) -> {
            cancelAction();
        });

        btnOK.setPreferredSize(btnCancel.getPreferredSize());
        //Finalizations
        pack();
    }

    /**
     * Populates all the UI components from the object in memory.
     */
    private void populateComponents() {
        this.tfName.setText(this.aSupplier.getNickname());
        this.tfAddress.setText(this.aSupplier.getUrl());
    }

    /**
     * Populates the object in memory from all the UI components.
     */
    private boolean populateObject() {
        boolean returnValue = false;
        try {
            this.aSupplier.setNickname(this.tfName.getText());
            this.aSupplier.setUrl(this.tfAddress.getText());
            returnValue = true;
        } catch (java.sql.SQLException | RuntimeException ex) {
            Utilities.setErrorMessage(ex);
            JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                    Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
        }

        return returnValue;
    }

    /**
     * Handles the save action. If any errors, then display error message
     * instead.
     */
    private void saveAction() {
        if (populateObject()) {
            if (this.bll.save(this.aSupplier)) {
                this.dialogResult = DialogResultType.OK;
                this.setVisible(false);
                this.dispose();
            } else {
                this.dialogResult = DialogResultType.CANCEL;
                JOptionPane.showMessageDialog(this, Utilities.getErrorMessage(),
                        Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handles the cancel action.
     */
    private void cancelAction() {
        this.dialogResult = DialogResultType.CANCEL;
        this.setVisible(false);
        this.dispose();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    /**
     * Displays the dialog.
     *
     * @return The DialogReturnType which tells how the dialog was closed.
     */
    public DialogResultType display() {
        System.out.println(String.format("Displaying %s...", WINDOW_NAME));
        setVisible(true);
        return this.dialogResult;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SubClasses">
    /**
     * Handles all aspects of closing the program.
     */
    private class CloseQuery extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            JDialog frame = SupplierDetailsDialog.this;
            int result = JOptionPane.showConfirmDialog(frame,
                    "Do you want to save?", "Close Query",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                saveAction();
            } else {
                cancelAction();
            }
        }
    }
    // </editor-fold>
}
