package trackit.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import trackit.*;

/**
 * UI Layer: Handles all aspects of the OrderItem Detail's UI.
 */
public class OrderItemDetailsUI
        extends ItemDetailsUI<OrderItem> {
    // <editor-fold defaultstate="collapsed" desc="Constants">

    // </editor-fold>
    // <editor-fold defaultstate="expanded" desc="Private Fields">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Components">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public OrderItemDetailsUI() {
        super("Order Item Details", new OrderItem());
        
        this.initializeComponents();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    protected void initializeComponents() {

        //TODO:  add additional components here.
        super.finalizeComponents();
    }

    @Override
    protected void actionSave() {
        if (!bal.save()) {
            //TODO:  display bal.getErrorMessage();
        }
    }

    @Override
    protected void actionCancel() {
        //TODO:  close window and return to prior window.
    }

    @Override
    protected void actionDelete() {
        if (bal.remove()) {
            //TODO:  close window and return to prior window.
        } else {
            //TODO:  display bal.getErrorMessage() and stay on this window.
        }
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    // </editor-fold>
}
