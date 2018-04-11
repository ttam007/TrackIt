/*lookups*/
CALL sp_Lookups_Insert ('listName', 'listValue');
CALL sp_Lookups_Select ('listName');
CALL sp_Lookups_Select (null);

CALL sp_Lookups_Update('listName', 'listValue2');
CALL sp_Lookups_Select ('listName');

CALL sp_Lookups_Delete ('listName');
CALL sp_Lookups_Select ('listName');
CALL sp_Lookups_Select (null);

CALL sp_Lookups_Insert ('listName', 'listValue3');
CALL sp_Lookups_Insert ('listName', 'listValue4');
CALL sp_Lookups_Insert ('listName', 'listValue3'); /*negative testing*/
CALL sp_Lookups_Select (null);

CALL sp_Lookups_Delete (null);
CALL sp_Lookups_Select (null);

/*inventoryItems*/
SET @inventoryItemId = null;
CALL sp_inventoryItems_Insert (@inventoryItemId, 5, '2018-04-16', 'Widget', '12345-ABC', '16 ounce', 'Available');
SELECT @inventoryItemId;
CALL sp_inventoryItems_Select (4);
CALL sp_inventoryItems_Select (null);

CALL sp_inventoryItems_Update(4, 10, '2018-05-26', 'Widget', '12345-ABC', '16 ounce', 'Discontinued');
CALL sp_inventoryItems_Select (4);
CALL sp_inventoryItems_Delete (4);
CALL sp_inventoryItems_Select (4);
CALL sp_inventoryItems_Select (null);

CALL sp_inventoryItems_Insert (@inventoryItemId, 10, '2018-04-16', 'Widget', '12345-ABC', '16 ounce', 'Available');
CALL sp_inventoryItems_Insert (@inventoryItemId, 10, '2018-04-16', 'Widget', '12345-ABC', '16 ounce', 'Available');
CALL sp_inventoryItems_Insert (@inventoryItemId, 11, '2018-04-16', 'Widget', '12345-ABC', '16 ounce', 'Junk'); /*negative testing*/
CALL sp_inventoryItems_Select (null);

CALL sp_inventoryItems_Delete (null);
CALL sp_inventoryItems_Select (null); 

/*suppliers*/
SET @supplierId = null;
CALL sp_Suppliers_Insert ('Bonds Busing', 'www.bbus.com', @supplierId);
SELECT @supplierId;
CALL sp_Suppliers_Select (4);
CALL sp_Suppliers_Select (null);

CALL sp_Suppliers_Update(4, 'Matts Busing', 'www.mbus.com');
CALL sp_Suppliers_Select (4);

CALL sp_Suppliers_Delete (4);
CALL sp_Suppliers_Select (4);
CALL sp_Suppliers_Select (null);

CALL sp_Suppliers_Insert ('Bonds Busing2', 'www.bbus.com', @supplierId);
CALL sp_Suppliers_Insert ('Bonds Busing3', 'www.bbus.com', @supplierId);
CALL sp_Suppliers_Select (null);

CALL sp_Suppliers_Delete (null);
CALL sp_Suppliers_Select (null);

/*orders*/
SET @orderId = null;
CALL sp_Orders_Insert ('March 2018', 1, 'Ordered', '2018-03-16', '2018-03-25', @orderId);
SELECT @orderId;
CALL sp_Orders_Select (4);
CALL sp_Orders_Select (null);

CALL sp_Orders_Update(4, 'Feb 2018', 2, 'Arrived', '2018-02-16', '2018-02-25');
CALL sp_Orders_Select (4);

CALL sp_Orders_Delete (4);
CALL sp_Orders_Select (4);
CALL sp_Orders_Select (null);

CALL sp_Orders_Insert (@orderId, 'March 2018', 2, 'Ordered', '2018-03-16', '2018-03-25');
CALL sp_Orders_Insert (@orderId, 'March 2018', 3, 'Ordered', '2018-03-16', '2018-03-25');
CALL sp_Orders_Insert (@orderId, 'March 2018', 100, 'Ordered', '2018-03-16', '2018-03-25'); /*negative testing*/
CALL sp_Orders_Select (null);

CALL sp_Orders_Delete (null);
CALL sp_Orders_Select (null);

/*orderItems*/
SET @orderId = null;
CALL sp_Orders_Insert (@orderId, 'March 2018', 1, 'Ordered', '2018-03-16', '2018-03-25');
CALL sp_Orders_Insert (@orderId, 'April 2018', 2, 'Ordered', '2018-03-16', '2018-03-25');
CALL sp_Orders_Insert (@orderId, 'May 2018', 3, 'Ordered', '2018-03-16', '2018-03-25');
CALL sp_Orders_Insert (@orderId, 'June 2018', 1, 'Ordered', '2018-03-16', '2018-03-25');
SET @orderItemId = null;
CALL sp_OrderItems_Insert (@orderItemId, @orderId, 5, 15.15, 75.75, 'Widget', '12345-ABC', '16 ounce', 'Available');
CALL sp_OrderItems_Insert (@orderItemId, @orderId, 5, 15.15, 75.75, 'Widget', '12345-ABC', '16 ounce', 'Available');
CALL sp_OrderItems_Insert (@orderItemId, @orderId, 5, 15.15, 75.75, 'Widget', '12345-ABC', '16 ounce', 'Available');
CALL sp_OrderItems_Insert (@orderItemId, @orderId, 5, 15.15, 75.75, 'Widget', '12345-ABC', '16 ounce', 'Available');
CALL sp_OrderItems_Insert (@orderItemId, @orderId, 5, 15.15, 75.75, 'Widget', '12345-ABC', '16 ounce', 'Available');
SELECT @orderItemId;
CALL sp_OrderItems_Select (4);
CALL sp_OrderItems_Select (null);

CALL sp_OrderItems_Update(4, 3, 15, 150, '10', 'Widget', '12345-ABC', '16 ounce', 'Discontinued');
CALL sp_OrderItems_Select (4);

CALL sp_OrderItems_Delete (4);
CALL sp_OrderItems_Select (4);
CALL sp_OrderItems_Select (null);

CALL sp_OrderItems_Insert (@orderItemId, 1, 10, 1, 10, 10, 'Widget', '12345-ABC', '16 ounce', 'Available');
CALL sp_OrderItems_Insert (@orderItemId, 1, 11, 2, 10, 20, 'Widget', '12345-ABC', '16 ounce', 'Available');
CALL sp_OrderItems_Insert (@orderItemId, 3, 11, 3, 10, 30, 'Widget', '12345-ABC', '16 ounce', 'Available');
CALL sp_OrderItems_Insert (@orderItemId, 3, 10, 4, 10, 40, 'Widget', '12345-ABC', '16 ounce', 'Available');
CALL sp_OrderItems_Insert (@orderItemId, 100, 10, 5, 10, 50, 'Widget', '12345-ABC', '16 ounce', 'Available'); /*negative testing*/
CALL sp_OrderItems_Insert (@orderItemId, 4, 100, 6, 10, 60, 'Widget', '12345-ABC', '16 ounce', 'Available'); /*negative testing*/
CALL sp_OrderItems_Select (null);

CALL sp_OrderItems_Delete (null);
CALL sp_OrderItems_Select (null);
