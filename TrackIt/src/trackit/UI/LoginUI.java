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
    private final Login bal = new Login();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    JPanel pnlMain = new JPanel();
    JLabel lblUsername, lblPassword, lblTitle, lblAccess;
    JTextField tfUsername;
    JPasswordField pfPassword;
    JButton btnSubmit;
    JPanel pnlNorth, pnlSouth, pnlCenter, pnlCentWest, pnlCentCenter, pnlCentSouth;
    String username, password;


    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public LoginUI() {
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
        int frameHeight = 150;
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(WINDOW_NAME);
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());
        this.setLayout(new BorderLayout()); 

        //Add all components here and set properties.
        //this.add(pnlMain);
        
        pnlNorth = new JPanel();
        lblTitle = new JLabel("Home Inventory Tracking System");
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
        pfPassword = new JPasswordField(20);
        passwordBx.add(pfPassword);
        Box submitBx = Box.createHorizontalBox();
        btnSubmit = new JButton("Log In");
        submitBx.add(btnSubmit);

        Box combine = Box.createVerticalBox();
        combine.add(usernameBx);
        combine.add(passwordBx);
        combine.add(submitBx);

        pnlCenter.add(combine);

        pnlSouth = new JPanel();
        lblAccess = new JLabel("");
        pnlSouth.add(lblAccess);
        add(lblAccess, BorderLayout.SOUTH);

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = tfUsername.getText().trim();
                password = pfPassword.getText().trim();

                if ("admin".equals(username) && "pwd".equals(password)) {
                    lblAccess.setText("Access granted");
                    setVisible(false);
                    MainMenuUI newFrame = new MainMenuUI();
                } else if (!"admin".equals(username)) {
                    lblAccess.setText("Access denied. Invalid username");
                } else if (!"pwd".equals(password)) {
                    lblAccess.setText("Access denied. Invalid password");
                } else {
                    lblAccess.setText("Access denied");
                }
            }
        });
        
        btnSubmit.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    username = tfUsername.getText().trim();
                    password = pfPassword.getText().trim();

                    if ("admin".equals(username) && "pwd".equals(password)) {
                        lblAccess.setText("Access granted");
                        setVisible(false);
                        MainMenuUI newFrame = new MainMenuUI();
                    } else if (!"admin".equals(username)) {
                        lblAccess.setText("Access denied. Invalid username");
                    } else if (!"pwd".equals(password)) {
                        lblAccess.setText("Access denied. Invalid password");
                    } else {
                        lblAccess.setText("Access denied");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });

        
        //Finalizations
        pack();
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
