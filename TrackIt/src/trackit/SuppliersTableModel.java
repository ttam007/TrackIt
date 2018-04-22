package trackit;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import trackit.UI.SuppliersPanel;

/**
 *
 * @author SLunsford
 */
public class SuppliersTableModel
        extends AbstractTableModel {

    private final String[] columnNames = SuppliersPanel.getColumnHeaders();
    private Object[] allSuppliers;

    /**
     * Default Constructor.
     */
    public SuppliersTableModel() {
        initializeVariables();

        /* Use this code if allSuppliers can be final.
        try {
            ArrayList<ASupplier> suppliers = ASupplier.loadAll();
            allSuppliers = suppliers.toArray();
        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }*/
    }

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

    private void initializeVariables() {
        try {
            ArrayList<ASupplier> suppliers = ASupplier.loadAll();
            allSuppliers = suppliers.toArray();
        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }
    }
}
