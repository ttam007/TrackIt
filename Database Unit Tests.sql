/*inventory*/
SET @inventoryId = null;
CALL sp_Inventory_Insert (8, '5', '2018-04-16', @inventoryId);
SELECT @inventoryId;
CALL sp_Inventory_Select (4);
CALL sp_Inventory_Select (null);

CALL sp_Inventory_Update(4, 8, '10', '2018-05-26');
CALL sp_Inventory_Select (4);

CALL sp_Inventory_Delete (4);
CALL sp_Inventory_Select (4);
CALL sp_Inventory_Select (null);

CALL sp_Inventory_Insert (9, '10', '2018-04-16', @inventoryId);
CALL sp_Inventory_Insert (10, '10', '2018-04-16', @inventoryId);
CALL sp_Inventory_Insert (100, '10', '2018-04-16', @inventoryId); /*negative testing*/
CALL sp_Inventory_Select (null);

CALL sp_Inventory_Delete (null);
CALL sp_Inventory_Select (null);

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
CALL sp_Items_Insert ('Widget', '12345-ABC', '16', 'ounce', 0, @itemId);
SELECT @itemId;
CALL sp_Items_Select (4);
CALL sp_Items_Select (null);

CALL sp_Items_Update(4, 'Widget v2', '12345-XYZ', '16', 'gallon', 0);
CALL sp_Items_Select (4);

CALL sp_Items_Delete (4);
CALL sp_Items_Select (4);
CALL sp_Items_Select (null);

CALL sp_Items_Insert ('Widget2', '12345-ABC', '16', 'ounce', 0, @itemId);
CALL sp_Items_Insert ('Widget3', '12345-ABC', '16', 'ounce', 0, @itemId);
CALL sp_Items_Insert ('Widget4', '12345-ABC', '16', 'ounces', 0, @itemId); /*negative testing*/
CALL sp_Items_Select (null);

CALL sp_Items_Delete (null);
CALL sp_Items_Select (null);

/*orderItems*/
SET @itemId = null;
CALL sp_Items_Insert ('Widget', '12345-ABC', '16', 'ounce', 0, @itemId);
SET @orderId = null;
CALL sp_Orders_Insert ('March 2018', '1', '2018-03-16', 'Ordered', '2018-03-25', @orderId);
SET @orderItemId = null;
CALL sp_OrderItems_Insert (@orderId, @itemId, '5', @orderItemId);
SELECT @orderItemId;
CALL sp_OrderItems_Select (4);
CALL sp_OrderItems_Select (null);

CALL sp_OrderItems_Update(4, 4, 10, '10');
CALL sp_OrderItems_Select (4);

CALL sp_OrderItems_Delete (4);
CALL sp_OrderItems_Select (4);
CALL sp_OrderItems_Select (null);

CALL sp_OrderItems_Insert (4, 10, '10', @orderItemId);
CALL sp_OrderItems_Insert (4, 11, '10', @orderItemId);
CALL sp_OrderItems_Insert (3, 11, '10', @orderItemId);
CALL sp_OrderItems_Insert (3, 10, '10', @orderItemId);
CALL sp_OrderItems_Insert (100, 10, '10', @orderItemId); /*negative testing*/
CALL sp_OrderItems_Insert (4, 100, '10', @orderItemId); /*negative testing*/
CALL sp_OrderItems_Select (null);

CALL sp_OrderItems_Delete (null);
CALL sp_OrderItems_Select (null);

/*orders*/
SET @orderId = null;
CALL sp_Orders_Insert ('March 2018', '1', '2018-03-16', 'Ordered', '2018-03-25', @orderId);
SELECT @orderId;
CALL sp_Orders_Select (4);
CALL sp_Orders_Select (null);

CALL sp_Orders_Update(4, 'Feb 2018', '2', '2018-02-16', 'Arrived', '2018-02-25');
CALL sp_Orders_Select (4);

CALL sp_Orders_Delete (4);
CALL sp_Orders_Select (4);
CALL sp_Orders_Select (null);

CALL sp_Orders_Insert ('March 2018', '2', '2018-03-16', 'Ordered', '2018-03-25', @orderId);
CALL sp_Orders_Insert ('March 2018', '3', '2018-03-16', 'Ordered', '2018-03-25', @orderId);
CALL sp_Orders_Insert ('March 2018', '4', '2018-03-16', 'Ordered', '2018-03-25', @orderId); /*negative testing*/
CALL sp_Orders_Select (null);

CALL sp_Orders_Delete (null);
CALL sp_Orders_Select (null);

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