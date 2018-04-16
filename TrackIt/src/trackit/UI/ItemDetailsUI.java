package trackit.UI;

import trackit.DAL.AnItem;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Super class of all windows that show AnItem Details.
 *
 * @param <T> The specific type of AnItem (Inventory or Order) that will be
 * handled by this class.
 */
public abstract class ItemDetailsUI<T extends AnItem>
        extends JFrame {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    /**
     *
     */
    protected final String WINDOW_NAME;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">

    /**
     *
     */
    protected T bal;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">

    /**
     *
     */
    protected JPanel pnlMain = new JPanel();

    /**
     *
     */
    protected JButton btnOK = new JButton();

    /**
     *
     */
    protected JButton btnCancel = new JButton();

    /**
     *
     */
    protected JButton btnDelete = new JButton();

    /**
     *
     * @param windowName
     * @param balLayer
     */
    protected ItemDetailsUI(String windowName, T balLayer) {
        this.WINDOW_NAME = windowName;
        this.bal = balLayer;

        initializeComponents();
    }

    /**
     * Sets up all components used in this frame.
     */
    private void initializeComponents() {
        //Setup main frame
        int frameWidth = 1200;
        int frameHeight = 600;
        Dimension dimFrame = new Dimension(frameWidth, frameHeight);
        this.setTitle(Utilities.getWindowCaption(WINDOW_NAME));
        this.setPreferredSize(dimFrame);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CloseQuery());

        //Add all components here and set properties.
    }

    /**
     * Must be called after initialize of all Components is done, including
     * child classes.
     */
    protected void finalizeComponents() {
        pack();
    }

    /**
     * save button
     */
    protected abstract void actionSave();/*{
         if (!bal.save()) {
                //TODO:  display bal.getErrorMessage();
            }
    }*/

    /**
     * cancel button
     */
    protected abstract void actionCancel();/* {
        //TODO:  close window and return to prior window.
    }*/

    /**
     * delete item
     */
    protected abstract void actionDelete();/* {
        if (bal.remove()) {
            //TODO:  close window and return to prior window.
        } else {
            //TODO:  display bal.getErrorMessage() and stay on this window.
        }
    }*/
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
    protected class CloseQuery extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            JFrame frame = (JFrame) e.getSource();
            int result = JOptionPane.showConfirmDialog(frame,
                    "Do you want to save?", "Close Query",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                //TODO
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            } else {
                //TODO
            }
        }
    }
    // </editor-fold>
}
