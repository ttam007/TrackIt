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

/*items*/
SET @itemId = null;
CALL sp_Items_Insert ('Widget', '12345-ABC', '16 ounce', 'Available', @itemId);
SELECT @itemId;
CALL sp_Items_Select (4);
CALL sp_Items_Select (null);

CALL sp_Items_Update(4, 'Widget v2', '12345-XYZ', '16 gallon', 'Available');
CALL sp_Items_Select (4);

CALL sp_Items_Delete (4);
CALL sp_Items_Select (4);
CALL sp_Items_Select (null);

CALL sp_Items_Insert ('Widget2', '12345-ABC', '16 ounce', 'Available', @itemId);
CALL sp_Items_Insert ('Widget3', '12345-ABC', '16 quarts', 'Available', @itemId);
CALL sp_Items_Insert ('Widget3', '12345-ABC', '16 gallons', 'Available', @itemId); /*negative testing*/
CALL sp_Items_Select (null);

CALL sp_Items_Delete (null);
CALL sp_Items_Select (null);

/*inventoryItems*/
SET @inventoryItemId = null;
CALL sp_inventoryItems_Insert (8, '5', '2018-04-16', @inventoryItemId);
SELECT @inventoryItemId;
CALL sp_inventoryItems_Select (4);
CALL sp_inventoryItems_Select (null);

CALL sp_inventoryItems_Update(4, 8, '10', '2018-05-26');
CALL sp_inventoryItems_Select (4);
CALL sp_inventoryItems_Delete (4);
CALL sp_inventoryItems_Select (4);
CALL sp_inventoryItems_Select (null);

CALL sp_inventoryItems_Insert (9, '10', '2018-04-16', @inventoryItemId);
CALL sp_inventoryItems_Insert (10, '10', '2018-04-16', @inventoryItemId);
CALL sp_inventoryItems_Insert (100, '10', '2018-04-16', @inventoryItemId); /*negative testing*/
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

CALL sp_Orders_Insert ('March 2018', 2, 'Ordered', '2018-03-16', '2018-03-25', @orderId);
CALL sp_Orders_Insert ('March 2018', 3, 'Ordered', '2018-03-16', '2018-03-25', @orderId);
CALL sp_Orders_Insert ('March 2018', 100, 'Ordered', '2018-03-16', '2018-03-25', @orderId); /*negative testing*/
CALL sp_Orders_Select (null);

CALL sp_Orders_Delete (null);
CALL sp_Orders_Select (null);

/*orderItems*/
SET @itemId = null;
CALL sp_Items_Insert ('Widget', '12345-ABC', '16 ounce', 'Available', @itemId);
SET @orderId = null;
CALL sp_Orders_Insert ('March 2018', 1, 'Ordered', '2018-03-16', '2018-03-25', @orderId);
SET @orderItemId = null;
CALL sp_OrderItems_Insert (@orderId, @itemId, 5, 15, 75, @orderItemId);
SELECT @orderItemId;
CALL sp_OrderItems_Select (4);
CALL sp_OrderItems_Select (null);

CALL sp_OrderItems_Update(4, 4, 10, 15, 150, '10');
CALL sp_OrderItems_Select (4);

CALL sp_OrderItems_Delete (4);
CALL sp_OrderItems_Select (4);
CALL sp_OrderItems_Select (null);

CALL sp_OrderItems_Insert (4, 10, 1, 10, 10, @orderItemId);
CALL sp_OrderItems_Insert (4, 11, 2, 10, 20, @orderItemId);
CALL sp_OrderItems_Insert (3, 11, 3, 10, 30, @orderItemId);
CALL sp_OrderItems_Insert (3, 10, 4, 10, 40, @orderItemId);
CALL sp_OrderItems_Insert (100, 10, 5, 10, 50, @orderItemId); /*negative testing*/
CALL sp_OrderItems_Insert (4, 100, 6, 10, 60, @orderItemId); /*negative testing*/
CALL sp_OrderItems_Select (null);

CALL sp_OrderItems_Delete (null);
CALL sp_OrderItems_Select (null);
