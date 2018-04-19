package trackit;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import trackit.DAL.SQLHelperInventoryItem;
import trackit.UI.InventoryItemsPanel;


/**
 * @author  Diaz
 * @purpose Handles the dynamic creation of the JTable
 */
public class InventoryItemsTableModel extends AbstractTableModel{
    SQLHelperInventoryItem helper = new SQLHelperInventoryItem();
    private final String[] columnNames = InventoryItemsPanel.getColumnNames();
    ArrayList<AnInventoryItem> inventoryItems = getSQL();
    Object[] allInventoryItems =  getSQL().toArray();



    @Override
    public int getRowCount() {
        return allInventoryItems.length;
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
        return allInventoryItems[i];
    }
    ArrayList<AnInventoryItem> getSQL() {
        try {
            System.out.println("\nSelectAll");
            inventoryItems = helper.selectAll();
        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }
        return inventoryItems;
    }
    public ArrayList<AnInventoryItem> getItems(){
        return this.inventoryItems;
    }
}
