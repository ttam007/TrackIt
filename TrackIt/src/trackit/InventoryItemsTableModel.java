package trackit;

import java.sql.SQLException;
import java.util.ArrayList;
import trackit.DAL.SQLHelperInventoryItem;



/**
 * @author  Diaz
 * @purpose Handles the dynamic creation of the JTable by getting data from DB
 */
public class InventoryItemsTableModel {
    SQLHelperInventoryItem helper = new SQLHelperInventoryItem();
    ArrayList<AnInventoryItem> inventoryItems = getSQL();

    ArrayList<AnInventoryItem> getSQL() {
        try {
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
