SET @inventoryItemId = null;
CALL sp_inventoryItems_Insert (@inventoryItemId, 5, '2018-04-16', 'Widget', '12345-ABC', '16 ounce', 'Available');
CALL sp_inventoryItems_Insert (@inventoryItemId, 10, '2018-04-16', 'Widget', '12345-ABC', '16 ounce', 'Available');
CALL sp_inventoryItems_Insert (@inventoryItemId, 10, '2018-04-16', 'Widget', '12345-ABC', '16 ounce', 'Available');

SET @supplierId = null;
CALL sp_Suppliers_Insert (@supplierId, 'Bonds Busing', 'www.bbus.com');
CALL sp_Suppliers_Insert (@supplierId, 'Bonds Busing2', 'www.bbus.com');
CALL sp_Suppliers_Insert (@supplierId, 'Etsy', 'www.etsy.com');

SET @orderId = null;
CALL sp_Orders_Insert (@orderId, 'March 2018', 1, 'Ordered', '2018-03-16', '2018-03-25');
CALL sp_Orders_Insert (@orderId, 'April 2018', 1, 'Ordered', '2018-03-16', '2018-03-25');
CALL sp_Orders_Insert (@orderId, 'May 2018', 1, 'Ordered', '2018-03-16', '2018-03-25');
CALL sp_Orders_Insert (@orderId, 'June 2018', 1, 'Ordered', '2018-03-16', '2018-03-25');
CALL sp_Orders_Insert (@orderId, 'July 2018', 2, 'Ordered', '2018-03-16', '2018-03-25');
CALL sp_Orders_Insert (@orderId, 'August 2018', 3, 'Ordered', '2018-03-16', '2018-03-25');

SET @orderItemId = null;
CALL sp_OrderItems_Insert (@orderItemId, 1, 1, 5, 15.10);
CALL sp_OrderItems_Insert (@orderItemId, 2, 2, 5, 15.20);
CALL sp_OrderItems_Insert (@orderItemId, 3, 1, 5, 15.30);
CALL sp_OrderItems_Insert (@orderItemId, 4, 2, 5, 15.40);
CALL sp_OrderItems_Insert (@orderItemId, 1, 1, 5, 15.50);
CALL sp_OrderItems_Insert (@orderItemId, 1, 1, 1, 10);
CALL sp_OrderItems_Insert (@orderItemId, 1, 2, 2, 10);
CALL sp_OrderItems_Insert (@orderItemId, 3, 3, 3, 10);
CALL sp_OrderItems_Insert (@orderItemId, 3, 1, 4, 10);
