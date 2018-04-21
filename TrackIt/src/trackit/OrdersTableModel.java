package trackit;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import trackit.UI.OrdersPanel;

/**
 *
 * @author SLunsford
 */
public class OrdersTableModel
        extends AbstractTableModel {

    private final String[] columnNames = OrdersPanel.getColumnHeaders();
    private Object[] allOrders;

    /**
     * Default Constructor.
     */
    public OrdersTableModel() {
        initializeVariables();
        /* Use this code if allOrders can be final.
        try {
            ArrayList<AnOrder> orders = AnOrder.loadAll();
            allOrders = orders.toArray();
        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }*/
    }

    @Override
    public int getRowCount() {
        return allOrders.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return allOrders[i];
    }

    private void initializeVariables() {
        try {
            ArrayList<AnOrder> orders = AnOrder.loadAll();
            allOrders = orders.toArray();
        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }
    }
}
