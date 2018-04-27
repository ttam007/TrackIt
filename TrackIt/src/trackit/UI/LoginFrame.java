package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Login's UI.
 *
 * @author Douglas
 */
public class LoginFrame
        extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    /**
     * The name of the window.
     */
    private static final String WINDOW_NAME = "Login";
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final Login bll = new Login();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JLabel lblUsername, lblPassword, lblTitle, lblAccess;
    JTextField tfUsername;
    JPanel pnlNorth, pnlSouth, pnlCenter, pnlCentWest, pnlCentCenter, pnlCentSouth;
    JPasswordField pfPassword;
    JButton btnLogin;
    String username, password;
    GridBagConstraints gbc = new GridBagConstraints();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Login
     */
    public LoginFrame() {
        initializeComponents();
        this.tfUsername.requestFocusInWindow();
    }

    /**
     * After constructing the class, sets the User Name textbox to the specified
     * value.
     *
     * @param userName The value to appear in the User Name textbox.
     */
    public LoginFrame(String userName) {
        this();

        this.tfUsername.setText(userName);
        this.pfPassword.requestFocusInWindow();
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
        int frameHeight = 150;
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());
        this.setLayout(new BorderLayout());

        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        gbc.insets = new Insets(2, 2, 5, 0);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblTitle = new JLabel(Utilities.PROGRAM_NAME_LONG);
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(lblTitle, gbc);

        // Item Name Label Initialized
        lblUsername = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblUsername, gbc);

        // Item Name Text Field
        tfUsername = new JTextField(25);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        add(tfUsername, gbc);

        // Initialize password label and text field
        lblPassword = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(lblPassword, gbc);

        pfPassword = new JPasswordField(25);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        add(pfPassword, gbc);


        // Init Ok Button
        btnLogin = new JButton(Utilities.BUTTON_LOGIN);
        this.getRootPane().setDefaultButton(btnLogin);
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(btnLogin, gbc);
        btnLogin.addActionListener((ActionEvent e) -> {
            if (this.bll.startLogin(this.tfUsername.getText().trim(), new String(this.pfPassword.getPassword()))) {
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error Logging in.  Error = " + Utilities.getErrorMessage(),
                        Utilities.ERROR_MSG_CAPTION, JOptionPane.ERROR_MESSAGE);
                if (this.bll.isTooManyLoginAttempts()) {
                    this.dispose();
                }
            }
        });

        //Finalizations
        this.pack();
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
    private static class CloseQuery extends WindowAdapter {

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
