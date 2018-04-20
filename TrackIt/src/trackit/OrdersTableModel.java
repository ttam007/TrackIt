/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import trackit.DAL.SQLHelperOrder;
import trackit.UI.OrdersPanel;

/**
 *
 * @author SLunsford
 */
public class OrdersTableModel extends AbstractTableModel {

    SQLHelperOrder helper = new SQLHelperOrder();
    private final String[] columnNames = OrdersPanel.getColumnNames();
    ArrayList<AnOrder> orders;
    Object[] allOrders = getSQL().toArray();

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

    ArrayList<AnOrder> getSQL() {
        try {
            System.out.println("\nSelectAll");
            orders = helper.selectAll();
        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }
        return orders;
    }

}
