package trackittestdriver;

import trackit.AnOrderItem;
import trackit.AnOrder;
import trackit.AnItem;
import trackit.AnInventoryItem;
import trackit.ASupplier;
import java.sql.*;
import java.util.*;
import trackit.*;
import trackit.DAL.*;

/**
 * Tests the DAL Layer.
 *
 * @author Bond, Steven
 */
public class DALTestDriver {

    private final String password;

    public DALTestDriver(String password) {
        this.password = password;
        setDefaultConnection();
    }

    private void setDefaultConnection() {
        String databaseLocation = "localhost";
        Integer port = 3306;
        String databaseName = "TrackItDB";
        String userName = "root";

        SQLConnector conn = SQLConnector.getInstance();
        try {
            conn.setConnectionString(databaseLocation, port, databaseName, userName, password);
            if (conn.isValidConnection()) {
                System.out.println("Valid Connection");
            } else {
                System.out.println("Invalid Connection");
            }
        } catch (Exception ex) {
            System.out.println("Error = " + ex.getLocalizedMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="SQL Connections">
    /**
     * Good for testing that the mysql-connector JAR files are loaded and one
     * can interact with the database.
     */
    public void testMySQLJarConnection() {
        System.out.println("\ntestMySQLJarConnection");

        try {
            String myDriver = "com.mysql.jdbc.Driver";
            Class.forName(myDriver);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/TrackItDB", "root", password)) {
                String query = "insert into lookups (listName, listValue) values (?, ?)";
                PreparedStatement prepStat = conn.prepareStatement(query);
                prepStat.setString(1, "toiletries");
                prepStat.setString(2, "soap");
                prepStat.execute();
            }
        } catch (ClassNotFoundException exCNF) {
            System.err.println("Class Not Found Exception detected!");
            System.err.println(exCNF.getMessage());
        } catch (SQLException exSQL) {
            System.err.println("SQL Exception detected!");
            System.err.println(exSQL.getMessage());
        }
    }

    /**
     * Test SQLConnector class.
     */
    public void testSQLConnector() {
        System.out.println("\ntestSQLConnector");

        //Change these values to do negative testing.
        String databaseLocation = "localhost";
        Integer port = 3306;
        String databaseName = "TrackItDB";
        String userName = "root";

        SQLConnector conn = SQLConnector.getInstance();
        try {
            //Only uncomment one of these to test the overloading.
            conn.setConnectionString(databaseLocation, port, databaseName, userName, password);
            //conn.setConnectionString(databaseLocation, port, databaseName, userName);
            //conn.setConnectionString(databaseLocation, port, databaseName);
            //conn.setConnectionString(userName, password);
            //conn.setConnectionString(userName);

            System.out.println("Username = " + conn.getUserName());

            /*Depending on valid in local variables and the overloaded method 
            used, one might or might not get a valid connection.  Must adjust 
            accordingly for the test being run.
             */
            if (conn.isValidConnection()) {
                System.out.println("Valid Connection");
                System.out.println("Connection = " + conn.getConnection());
            } else {
                System.out.println("Invalid Connection");
                System.out.println("SQLConnector's errorMessage = " + conn.getErrorMessage());
            }

        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
            System.out.println("SQLConnector's errorMessage = " + conn.getErrorMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SQLHelperSupplier Class">
    private void printSupplier(ASupplier aSupplier) {
        if (aSupplier == null) {
            System.out.println("Supplier is null.");
        } else {
            System.out.println(String.format("Supplier:  PK = %d; nickname = %s; URL = %s",
                    aSupplier.getPrimaryKey(), aSupplier.getNickname(), aSupplier.getUrl()));
        }
    }

    private void printOrderItem(AnOrderItem anOrderItem) {
        if (anOrderItem == null) {
            System.out.println("OrderItem is null.");
        } else {
            System.out.println(String.format("OrderItem:  PK = %d; Order ID = %d;"
                    + " Quantity Ordered = %d; quantity checked in = %d; Price = %f;"
                    + " Extended Price = %f",
                    anOrderItem.getPrimaryKey(), anOrderItem.getOrderId(),
                    anOrderItem.getQuantityOrdered(), anOrderItem.getQuantityCheckedIn(),
                    anOrderItem.getPrice(), anOrderItem.getExtendedPrice()));
        }
    }

    private void printOrder(AnOrder anOrder) {
        if (anOrder == null) {
            System.out.println("Order is null.");
        } else {
            System.out.println(String.format("Order:  PK = %d; Description = %s; "
                    + "Ordered From = %s; Order Status = %s; Date Ordered = %s; "
                    + "Extended Price = %s",
                    anOrder.getPrimaryKey(), anOrder.getDescription(),
                    anOrder.getOrderedFrom(), anOrder.getOrderStatus(),
                    anOrder.getDateOrdered(), anOrder.getDateExpected()));
        }
    }

    private void printItem(AnItem anItem) {
        if (anItem == null) {
            System.out.println("Order is null.");
        } else {
            System.out.println(String.format("Order:  PK = %d; Description = %s; "
                    + "SKU = %s; Size Unit = %s; Item Status = %s;",
                    anItem.getPrimaryKey(), anItem.getDescription(),
                    anItem.getSku(), anItem.getSizeUnit(),
                    anItem.getItemStatus()));
        }
    }

    private void printInventoryItem(AnInventoryItem anInventoryItem) {
        if (anInventoryItem == null) {
            System.out.println("Order is null.");
        } else {
            System.out.println(String.format("Order:  PK = %d; Description = %s; "
                    + "SKU = %s; Size Unit = %s; Item Status = %s;",
                    anInventoryItem.getPrimaryKey(), anInventoryItem.getDescription(),
                    anInventoryItem.getSku(), anInventoryItem.getSizeUnit(),
                    anInventoryItem.getItemStatus()));
        }
    }

    /**
     * Tests all CRUD operations for the ASupplier's sprocs.
     */
    public void testSQLHelperSupplier() {
        System.out.println("\n\ntestSQLHelperSupplier");

        SQLHelperSupplier helper = new SQLHelperSupplier();
        ASupplier aSupplier;
        Integer pk;
        try {
            System.out.println("\nSelectAll");
            ArrayList<ASupplier> suppliers = helper.selectAll();
            for (ASupplier anItem : suppliers) {
                printSupplier(anItem);
            }

            System.out.println("\nSelectOne");
            aSupplier = helper.selectOne(2);
            printSupplier(aSupplier);

            System.out.println("\nInsert");
            //Must delete "Etsy" from the database before testing this.
            ASupplier aSupplierInsert = new ASupplier();
            aSupplierInsert.setNickname("Etsy");
            aSupplierInsert.setUrl("https://www.etsy.com");
            pk = helper.insert(aSupplierInsert);
            aSupplier = helper.selectOne(pk);
            printSupplier(aSupplier);

            System.out.println("\nUpdate");
            ASupplier aSupplierUpdate = new ASupplier();
            aSupplierUpdate.setPrimaryKey(pk);
            aSupplierUpdate.setNickname("Etsy");
            aSupplierUpdate.setUrl("https://www.etsy-orders.com");
            helper.update(aSupplierUpdate);
            aSupplier = helper.selectOne(aSupplierUpdate.getPrimaryKey());
            printSupplier(aSupplier);

            System.out.println("\nDelete");
            pk = 2;
            helper.delete(pk);
            aSupplier = helper.selectOne(pk);
            printSupplier(aSupplier);
        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }
    }

    /**
     * Tests all CRUD operations for the AnOrderItem's sprocs.
     */
    public void testSQLHelperOrderItem() {
        System.out.println("\n\ntestSQLHelperOrderItem");

        SQLHelperOrderItem helper = new SQLHelperOrderItem();
        AnOrderItem anOrderItem;
        Integer pk;
        try {
            System.out.println("\nSelectAll");
            ArrayList<AnOrderItem> orderItems = helper.selectAll();
            for (AnOrderItem anItem : orderItems) {
                printOrderItem(anItem);
            }

            System.out.println("\nSelectOne");
            anOrderItem = helper.selectOne(2);
            printOrderItem(anOrderItem);

            System.out.println("\nInsert");
            AnOrderItem anOrderItemInsert = new AnOrderItem();
            anOrderItemInsert.setOrderId(3);
            anOrderItemInsert.setItemId(1);
            anOrderItemInsert.setQuantityOrdered(3);
            anOrderItemInsert.setPrice(2.39);
            pk = helper.insert(anOrderItemInsert);
            anOrderItem = helper.selectOne(pk);
            printOrderItem(anOrderItem);

            System.out.println("\nUpdate");
            AnOrderItem anOrderItemUpdate = new AnOrderItem();
            anOrderItemUpdate.setPrimaryKey(pk);
            anOrderItemUpdate.setQuantityOrdered(20);
            anOrderItemUpdate.setQuantityCheckedIn(10);
            anOrderItemUpdate.setPrice(5.05);
            helper.update(anOrderItemUpdate);
            anOrderItem = helper.selectOne(anOrderItemUpdate.getPrimaryKey());
            printOrderItem(anOrderItem);

            System.out.println("\nDelete");
            pk = 2;
            helper.delete(pk);
            anOrderItem = helper.selectOne(pk);
            printOrderItem(anOrderItem);
        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }
    }

    /**
     * Tests all CRUD operations for the AnOrder's sprocs.
     */
    public void testSQLHelperOrder() {
        System.out.println("\n\ntestSQLHelperOrder");

        SQLHelperOrder helper = new SQLHelperOrder();
        AnOrder anOrder;
        Integer pk;
        try {
            System.out.println("\nSelectAll");
            ArrayList<AnOrder> order = helper.selectAll();
            for (AnOrder anItem : order) {
                printOrder(anItem);
            }

            System.out.println("\nSelectOne");
            anOrder = helper.selectOne(2);
            printOrder(anOrder);

            System.out.println("\nInsert");
            AnOrder anOrderInsert = new AnOrder();
            anOrderInsert.setDescription("Cleaning Supplies");
            anOrderInsert.setOrderedFrom(2);
            anOrderInsert.setOrderStatus("Created");
            anOrderInsert.setDateOrdered(Utilities.convertToSQLDate("2018-04-15"));
            pk = helper.insert(anOrderInsert);
            anOrder = helper.selectOne(pk);
            printOrder(anOrder);

            System.out.println("\nUpdate");
            AnOrder anOrderUpdate = new AnOrder();
            anOrderUpdate.setPrimaryKey(pk);
            anOrderUpdate.setDescription("Cleaning Supplies");
            anOrderUpdate.setOrderedFrom(2);
            anOrderUpdate.setOrderStatus(OrderStatusType.SHIPPED);
            helper.update(anOrderUpdate);
            anOrder = helper.selectOne(anOrderUpdate.getPrimaryKey());
            printOrder(anOrder);

            System.out.println("\nDelete");
            pk = 2;
            helper.delete(pk);
            anOrder = helper.selectOne(pk);
            printOrder(anOrder);
        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }
    }

    /**
     * Tests all CRUD operations for the AnInventoryItem's sprocs.
     */
    public void testSQLHelperInventoryItem() {
        System.out.println("\n\ntestSQLHelperInventoryItem");

        SQLHelperInventoryItem helper = new SQLHelperInventoryItem();
        AnInventoryItem anInventoryItem;
        Integer pk;
        try {
            System.out.println("\nSelectAll");
            ArrayList<AnInventoryItem> inventoryItem = helper.selectAll();
            for (AnInventoryItem anItem : inventoryItem) {
                printInventoryItem(anItem);
            }

            System.out.println("\nSelectOne");
            anInventoryItem = helper.selectOne(2);
            printInventoryItem(anInventoryItem);

            System.out.println("\nInsert");
            //Must delete "Etsy" from the database before testing this.
            AnInventoryItem anInventoryItemInsert = new AnInventoryItem();
            anInventoryItemInsert.setDescription("toothpaste");
            anInventoryItemInsert.setSku("4352");
            anInventoryItemInsert.setSizeUnit("1 tube");
            anInventoryItemInsert.setItemStatus("Available");
            pk = helper.insert(anInventoryItemInsert);
            anInventoryItem = helper.selectOne(pk);
            printInventoryItem(anInventoryItem);

            System.out.println("\nUpdate");
            AnInventoryItem anInventoryItemUpdate = new AnInventoryItem();
            anInventoryItemUpdate.setPrimaryKey(pk);
            anInventoryItemUpdate.setDescription("toothpaste");
            anInventoryItemUpdate.setSku("4352");
            anInventoryItemUpdate.setSizeUnit("box");
            anInventoryItemUpdate.setItemStatus(ItemStatusType.DO_NOT_ORDER);
            helper.update(anInventoryItemUpdate);
            anInventoryItem = helper.selectOne(anInventoryItemUpdate.getPrimaryKey());
            printInventoryItem(anInventoryItem);

            System.out.println("\nDelete");
            pk = 2;
            helper.delete(pk);
            anInventoryItem = helper.selectOne(pk);
            printInventoryItem(anInventoryItem);
        } catch (SQLException exSQL) {
            System.out.println("SQL error = " + exSQL.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println("Generic error = " + ex.getLocalizedMessage());
        }
    }
}
