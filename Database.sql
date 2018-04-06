/***********************************************************************
	Schema
***********************************************************************/

CREATE SCHEMA IF NOT EXISTS TrackItDB; 
USE TrackItDB;

/***********************************************************************
	Drops - Remove this section from installation script.
***********************************************************************/
DELIMITER $$

DROP PROCEDURE IF EXISTS DropFK $$ 
CREATE PROCEDURE DropFK ( 
	IN parm_table_name VARCHAR(100), 
	IN parm_key_name VARCHAR(100) 
) 
BEGIN 
	-- Verify the foreign key exists 
	IF EXISTS (SELECT NULL
		FROM information_schema.TABLE_CONSTRAINTS
        WHERE CONSTRAINT_SCHEMA = DATABASE()
			AND CONSTRAINT_NAME = parm_key_name) THEN
            
		-- Turn the parameters into local variables.
		SET @ParmTable = parm_table_name;
		SET @ParmKey = parm_key_name;

		-- Create the full statement to execute.
		SET @StatementToExecute = CONCAT('ALTER TABLE ',@ParmTable,' DROP FOREIGN KEY ',@ParmKey); 

		-- Prepare and execute the statement that was built.
		PREPARE DynamicStatement FROM @StatementToExecute; 
		EXECUTE DynamicStatement; 

		-- Cleanup the prepared statement 
		DEALLOCATE PREPARE DynamicStatement; 
	END IF; 
END $$ 

DELIMITER ; $$
DELIMITER ;

/*lookups*/
CALL DropFK('items', 'fk_items_lookups_sizeUnit');
CALL DropFK('items', 'fk_items_lookups_itemStatus');
CALL DropFK('orders', 'fk_orders_lookups_orderStatus');
DROP TABLE IF EXISTS lookups;

/*suppliers*/
CALL DropFK ('orders', 'fk_orders_suppliers_orderedFrom');
DROP TABLE IF EXISTS suppliers;

/*orders*/
CALL DropFK ('orderItems', 'fk_orderItems_orders_orderId');
DROP TABLE IF EXISTS orders;

/*items*/
CALL DropFK ('orderItems', 'fk_orderItems_items_itemId');
CALL DropFK ('inventoryItems', 'fk_inventoryItems_items_itemId');
DROP TABLE IF EXISTS items;

/*orderItems*/
DROP TABLE IF EXISTS orderItems;

/*inventoryItems*/
DROP TABLE IF EXISTS inventoryItems;

/***********************************************************************
	Tables
***********************************************************************/

DELIMITER ;

CREATE TABLE lookups (
    listName VARCHAR(32) NOT NULL,
    listValue VARCHAR(32) NOT NULL,
    PRIMARY KEY (listName, listValue),
    INDEX idx_lookups_listValue (listValue)
    );

CREATE TABLE suppliers (
    supplierId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nickname VARCHAR(32) NOT NULL,
    URL VARCHAR(256) NULL,
    PRIMARY KEY (supplierId),
    UNIQUE idx_suppliers_nickname (nickname)
);
    
CREATE TABLE orders (
    orderId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    description VARCHAR (64) NOT NULL,
    orderedFrom INT UNSIGNED NOT NULL,
    orderStatus VARCHAR (32) NOT NULL,
    dateOrdered DATE NOT NULL,
    dateExpected DATE NULL,
    PRIMARY KEY (orderId),
    CONSTRAINT fk_orders_suppliers_orderedFrom FOREIGN KEY (orderedFrom) REFERENCES suppliers(supplierId),
    CONSTRAINT fk_orders_lookups_orderStatus FOREIGN KEY (orderStatus) REFERENCES lookups(listValue)
   );

CREATE TABLE items (
    itemId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    description VARCHAR (64) NOT NULL,
    sku VARCHAR(32) NULL,
    sizeUnit VARCHAR(32) NULL,
    itemStatus VARCHAR(32) NOT NULL,
    PRIMARY KEY (itemId),
    CONSTRAINT fk_items_lookups_itemStatus FOREIGN KEY (itemStatus) REFERENCES lookups(listValue)
    );
    
CREATE TABLE orderItems (
    orderItemId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    orderId INT UNSIGNED NOT NULL,
    itemId INT UNSIGNED NOT NULL,
    quantityOrdered INT UNSIGNED NOT NULL DEFAULT 1,
    price FLOAT(8,4) UNSIGNED NOT NULL DEFAULT 0,
    extendedPrice FLOAT(10,4) UNSIGNED NOT NULL DEFAULT 0,
    PRIMARY KEY (orderItemId),
    CONSTRAINT fk_orderItems_orders_orderId FOREIGN KEY (orderId) REFERENCES orders (orderId),
    CONSTRAINT fk_orderItems_items_itemId FOREIGN KEY (itemId) REFERENCES items (itemId)
);

CREATE TABLE inventoryItems (
    inventoryItemId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    itemId INT UNSIGNED NOT NULL,
    quantity INT UNSIGNED NOT NULL DEFAULT 0,
    expirationDate DATE NULL,
    PRIMARY KEY (inventoryItemId),
    CONSTRAINT fk_inventoryItems_items_itemId FOREIGN KEY (itemId) REFERENCES items (itemId)
);

/***********************************************************************
	Stored Procedures
***********************************************************************/

DELIMITER ;;

/*lookups*/
DROP PROCEDURE IF EXISTS sp_Lookups_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Lookups_Select (
	IN listName VARCHAR(32)
)
BEGIN
	IF (listName IS NULL) THEN
    	SELECT *
		FROM lookups;
    ELSE
    	SELECT *
		FROM lookups
		WHERE lookups.listName = listName;
	END IF;
END;;

DROP PROCEDURE IF EXISTS sp_Lookups_Insert;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Lookups_Insert (
	IN listName VARCHAR(32),
    IN listValue VARCHAR(32)
)
BEGIN
	INSERT INTO lookups(listName, listValue)
	VALUES(listName, listValue);
    
    /*PK is listName, so no need to return it.*/
END;;

DROP PROCEDURE IF EXISTS sp_Lookups_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Lookups_Update (
	IN listName VARCHAR(32),
    IN listValue VARCHAR(32)
)
BEGIN
	UPDATE lookups
    SET lookups.listValue = listValue
	WHERE lookups.listName = listName;
END;;

DROP PROCEDURE IF EXISTS sp_Lookups_Delete;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Lookups_Delete (
	IN listName VARCHAR(32)
)
BEGIN
	IF (listName IS NULL) THEN
		DELETE FROM lookups;
	ELSE
		DELETE FROM lookups
		WHERE lookups.listName = listName;
	END IF;
END;;

/*suppliers*/
DROP PROCEDURE IF EXISTS sp_Suppliers_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Suppliers_Select (
	IN supplierId INT UNSIGNED
)
BEGIN
	IF (supplierId IS NULL) THEN
    	SELECT *
		FROM suppliers;
    ELSE
    	SELECT *
		FROM suppliers
		WHERE suppliers.supplierId = supplierId;
	END IF;
END;;

DROP PROCEDURE IF EXISTS sp_Suppliers_Insert;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Suppliers_Insert (
	IN nickname VARCHAR(32),
    IN URL VARCHAR(256),
    OUT supplierId INT UNSIGNED
)
BEGIN
	INSERT INTO suppliers(nickname, URL)
	VALUES(nickname, URL);
    
    SET supplierId = LAST_INSERT_ID();
END;;

DROP PROCEDURE IF EXISTS sp_Suppliers_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Suppliers_Update (
	IN supplierId INT UNSIGNED,
	IN nickname VARCHAR(32),
    IN URL VARCHAR(256)
)
BEGIN
	UPDATE suppliers
    SET suppliers.nickname = nickname,
		suppliers.URL = URL
	WHERE suppliers.supplierId = supplierId;
END;;

DROP PROCEDURE IF EXISTS sp_Suppliers_Delete;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Suppliers_Delete (
	IN supplierId INT UNSIGNED
)
BEGIN
	IF (supplierId IS NULL) THEN
		DELETE FROM suppliers;
	ELSE
		DELETE FROM suppliers
		WHERE suppliers.supplierId = supplierId;
	END IF;
END;;

/*orders*/
DROP PROCEDURE IF EXISTS sp_Orders_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Orders_Select (
	IN orderId INT UNSIGNED
)
BEGIN
	IF (orderId IS NULL) THEN
    	SELECT *
		FROM orders;
    ELSE
    	SELECT *
		FROM orders
		WHERE orders.orderId = orderId;
	END IF;
END;;

DROP PROCEDURE IF EXISTS sp_Orders_Insert;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Orders_Insert (
	IN description VARCHAR (64),
	IN orderedFrom INT UNSIGNED,
    IN orderStatus VARCHAR (32),
    IN dateOrdered DATE,
    IN dateExpected DATE,
    OUT orderId INT UNSIGNED
)
BEGIN
	INSERT INTO orders (description, orderedFrom, orderStatus, dateOrdered, dateExpected)
    VALUES (description, orderedFrom, orderStatus, dateOrdered, dateExpected);
    
    SET orderId = LAST_INSERT_ID();
END;;

DROP PROCEDURE IF EXISTS sp_Orders_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Orders_Update (
	IN orderId INT UNSIGNED,
	IN description VARCHAR (64),
    IN orderedFrom INT UNSIGNED,
    IN orderStatus VARCHAR (32),
    IN dateOrdered DATE,
    IN dateExpected DATE
)
BEGIN
	UPDATE orders
    SET orders.description = description,
		orders.orderedFrom = orderedFrom,
        orders.orderStatus = orderStatus,
        orders.dateOrdered = dateOrdered,
        orders.dateExpected = dateExpected
	WHERE orders.orderId = orderId;
END;;

DROP PROCEDURE IF EXISTS sp_Orders_Delete;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Orders_Delete (
	IN orderId INT UNSIGNED
)
BEGIN
	IF (orderId IS NULL) THEN
		DELETE FROM orders;
	ELSE
		DELETE FROM orders
		WHERE orders.orderId = orderId;
	END IF;
END;;

/*items*/
DROP PROCEDURE IF EXISTS sp_Items_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Items_Select (
	IN itemId INT UNSIGNED
)
BEGIN
	IF (itemId IS NULL) THEN
    	SELECT *
		FROM items;
    ELSE
    	SELECT *
		FROM items
		WHERE items.itemId = itemId;
	END IF;
END;;

DROP PROCEDURE IF EXISTS sp_Items_Insert;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Items_Insert (
	IN description VARCHAR(64),
    IN sku VARCHAR(32),
    IN sizeUnit VARCHAR(32),
    IN itemStatus VARCHAR(32),
    OUT itemId INT UNSIGNED
)
BEGIN
	INSERT INTO items (description, sku, sizeUnit, itemStatus)
    VALUES (description, sku, sizeUnit, itemStatus);
    
    SET itemId = LAST_INSERT_ID();
END;;

DROP PROCEDURE IF EXISTS sp_Items_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Items_Update (
	IN itemId INT UNSIGNED,
	IN description VARCHAR(64),
    IN sku VARCHAR(32),
    IN sizeUnit VARCHAR(32),
    IN itemStatus VARCHAR(32)
)
BEGIN
	UPDATE items
    SET items.description = description,
		items.sku = sku,
        items.sizeUnit = sizeUnit,
        items.itemStatus = itemStatus
	WHERE items.itemId = itemId;
END;;

DROP PROCEDURE IF EXISTS sp_Items_Delete;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Items_Delete (
	IN itemId INT UNSIGNED
)
BEGIN
	IF (itemId IS NULL) THEN
		DELETE FROM items;
	ELSE
		DELETE FROM items
		WHERE items.itemId = itemId;
	END IF;
END;;

/*orderItems*/
DROP PROCEDURE IF EXISTS sp_OrderItems_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_OrderItems_Select (
	IN orderItemId INT UNSIGNED
)
BEGIN
	IF (orderItemId IS NULL) THEN
    	SELECT *
		FROM orderItems;
    ELSE
    	SELECT *
		FROM orderitems
		WHERE orderitems.orderItemId = orderItemId;
	END IF;
END;;

DROP PROCEDURE IF EXISTS sp_OrderItems_Insert;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_OrderItems_Insert (
	IN orderId INT UNSIGNED,
    IN itemId INT UNSIGNED,
    IN quantityOrdered INT UNSIGNED,
    IN price FLOAT(8,4) UNSIGNED,
    IN extendedPrice FLOAT(10,4) UNSIGNED,
    OUT orderItemId INT UNSIGNED
)
BEGIN
	INSERT INTO orderItems (orderId, itemId, quantityOrdered, price, extendedPrice)
    VALUES (orderId, itemId, quantityOrdered, price, extendedPrice);
    
	SET orderItemId = LAST_INSERT_ID(); 
END;;

DROP PROCEDURE IF EXISTS sp_OrderItems_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_OrderItems_Update (
	IN orderItemId INT UNSIGNED,
	IN orderId INT UNSIGNED,
    IN itemId INT UNSIGNED,
    IN quantityOrdered INT UNSIGNED,
    IN price FLOAT(8,4) UNSIGNED,
    IN extendedPrice FLOAT(10,4) UNSIGNED
)
BEGIN
	UPDATE orderitems
    SET orderitems.orderId = orderId,
		orderitems.itemId = itemId,
        orderitems.quantityOrdered = quantityOrdered,
        orderitems.price = price,
        orderitems.extendedPrice = extendedPrice
	WHERE orderitems.orderItemId = orderItemId;
END;;

DROP PROCEDURE IF EXISTS sp_OrderItems_Delete;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_OrderItems_Delete (
	IN orderItemId INT UNSIGNED
)
BEGIN
	IF (orderItemId IS NULL) THEN
		DELETE FROM orderitems;
	ELSE
		DELETE FROM orderitems
		WHERE orderitems.orderItemId = orderItemId;
	END IF;
END;;

/*inventoryItems*/
DROP PROCEDURE IF EXISTS sp_InventoryItems_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_InventoryItems_Select (
	IN InventoryItemId INT UNSIGNED
)
BEGIN
	IF (InventoryItemId IS NULL) THEN
    	SELECT *
		FROM InventoryItems;
    ELSE
    	SELECT *
		FROM InventoryItems
		WHERE InventoryItems.InventoryItemId = inventoryItemId;
	END IF;
END;;

DROP PROCEDURE IF EXISTS sp_InventoryItems_Insert;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_InventoryItems_Insert (
	IN itemId INT UNSIGNED,
    IN quantity INT UNSIGNED,
    IN expirationDate DATE,
    OUT inventoryItemId INT UNSIGNED
)
BEGIN
	INSERT INTO inventoryItems(itemId, quantity, expirationDate)
	VALUES(itemId, quantity, expirationDate);
    
    SET inventoryItemId = LAST_INSERT_ID();
END;;

DROP PROCEDURE IF EXISTS sp_InventoryItems_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_InventoryItems_Update (
	IN InventoryItemId INT UNSIGNED,
	IN itemId INT UNSIGNED,
    IN quantity INT UNSIGNED,
    IN expirationDate DATE
)
BEGIN
	UPDATE InventoryItems
    SET inventoryItems.itemId = itemId,
		inventoryItems.quantity = quantity,
        inventoryItems.expirationDate = expirationDate
	WHERE inventoryItems.inventoryItemId = inventoryItemId;
END;;

DROP PROCEDURE IF EXISTS sp_InventoryItems_Delete;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_InventoryItems_Delete (
	IN inventoryItemId INT UNSIGNED
)
BEGIN
	IF (inventoryItemId IS NULL) THEN
		DELETE FROM inventoryItems;
	ELSE
		DELETE FROM inventoryItems
		WHERE inventoryItems.inventoryItemId = inventoryItemId;
	END IF;
END;;

/***********************************************************************
	Default Data
***********************************************************************/

DELIMITER ;

/*lookups*/
INSERT INTO lookups (listName, listValue) VALUES ('orderStatuses', 'Ordered');
INSERT INTO lookups (listName, listValue) VALUES ('orderStatuses', 'Shipping');
INSERT INTO lookups (listName, listValue) VALUES ('orderStatuses', 'Arrived');
INSERT INTO lookups (listName, listValue) VALUES ('itemStatuses', 'Available');
INSERT INTO lookups (listName, listValue) VALUES ('itemStatuses', 'Discontinued');
INSERT INTO lookups (listName, listValue) VALUES ('itemStatuses', 'Do Not Replace');

/*suppliers*/
INSERT INTO suppliers (nickname, URL) VALUES ('Amazon', 'https://www.amazon.com/');
INSERT INTO suppliers (nickname, URL) VALUES ('Wal-Mart', 'https://www.walmart.com/');
INSERT INTO suppliers (nickname, URL) VALUES ('Target', 'https://www.target.com/');
