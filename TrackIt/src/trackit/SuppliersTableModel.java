/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackit;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import trackit.DAL.SQLHelperSupplier;
import trackit.UI.SuppliersPanel;

/**
 *
 * @author SLunsford
 */
public class SuppliersTableModel extends AbstractTableModel {

    SQLHelperSupplier helper = new SQLHelperSupplier();
    private final String[] columnNames = SuppliersPanel.getColumnNames();
    ArrayList<ASupplier> suppliers;
    Object[] allSuppliers = getSQL().toArray();

    @Override
    public int getRowCount() {
        return allSuppliers.length;
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
        return allSuppliers[i];
    }

    ArrayList<ASupplier> getSQL() {
        try {
            System.out.println("\nSelectAll");
            suppliers = helper.selectAll();
        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }
        return suppliers;
    }

}
