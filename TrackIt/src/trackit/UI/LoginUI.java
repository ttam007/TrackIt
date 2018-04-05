package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the Login's UI.
 */
public class LoginUI
        extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    private static final String WINDOW_NAME = "Login";
    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    private final Login bll = new Login();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlMain = new JPanel();
    JLabel lblUsername, lblPassword, lblTitle, lblAccess;
    JTextField tfUsername, tfPassword;
    JButton btnOK;
    JPanel pnlNorth, pnlSouth, pnlCenter, pnlCentWest, pnlCentCenter, pnlCentSouth;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public LoginUI() {
        initializeComponents();
    }

    /**
     * After constructing the class, sets the User Name textbox to the specified
     * value.
     *
     * @param userName The value to appear in the User Name textbox.
     */
    public LoginUI(String userName) {
        this();

        this.tfUsername.setText(userName);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
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

        //Add all components here and set properties.
        this.add(pnlMain);

        pnlNorth = new JPanel();
        lblTitle = new JLabel(Utilities.PROGRAM_NAME_LONG);
        pnlNorth.add(lblTitle);
        add(pnlNorth, BorderLayout.NORTH);

        pnlCenter = new JPanel();
        add(pnlCenter, BorderLayout.CENTER);

        Box usernameBx = Box.createHorizontalBox();
        lblUsername = new JLabel("Username: ");
        usernameBx.add(lblUsername);
        tfUsername = new JTextField(20);
        usernameBx.add(tfUsername);
        Box passwordBx = Box.createHorizontalBox();
        lblPassword = new JLabel("Password: ");
        passwordBx.add(lblPassword);
        tfPassword = new JTextField(20);
        passwordBx.add(tfPassword);
        Box submitBx = Box.createHorizontalBox();
        btnOK = new JButton("Log In");
        submitBx.add(btnOK);

        Box combine = Box.createVerticalBox();
        combine.add(usernameBx);
        combine.add(passwordBx);
        combine.add(submitBx);

        pnlCenter.add(combine);

        pnlSouth = new JPanel();
        lblAccess = new JLabel("");
        pnlSouth.add(lblAccess);
        add(lblAccess, BorderLayout.SOUTH);

        btnOK.addActionListener((ActionEvent e) -> {
            if (this.bll.startLogin(this.tfUsername.getText().trim(), this.tfPassword.getText().trim())) {
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error Logging in.  Error = " + bll.getErrorMessage(), "Error",
                        JOptionPane.OK_OPTION);
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
        System.out.println(String.format("Displaying {0}...", WINDOW_NAME));
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
