/***********************************************************************
	Schema
***********************************************************************/

CREATE SCHEMA IF NOT EXISTS TrackItDB; 
USE TrackItDB;

/***********************************************************************
	Drops - Remove this section from installation script.
***********************************************************************/
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_FK_Drop $$ 
CREATE PROCEDURE sp_FK_Drop ( 
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
CALL sp_FK_Drop('items', 'fk_items_lookups_sizeUnit');
CALL sp_FK_Drop('items', 'fk_items_lookups_itemStatus');
CALL sp_FK_Drop('orders', 'fk_orders_lookups_orderStatus');
DROP TABLE IF EXISTS lookups;

/*suppliers*/
CALL sp_FK_Drop ('orders', 'fk_orders_suppliers_orderedFrom');
DROP TABLE IF EXISTS suppliers;

/*orders*/
CALL sp_FK_Drop ('orderItems', 'fk_orderItems_orders_orderId');
DROP TABLE IF EXISTS orders;

/*items*/
CALL sp_FK_Drop ('orderItems', 'fk_orderItems_items_itemId');
CALL sp_FK_Drop ('inventoryItems', 'fk_inventoryItems_items_itemId');
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
    url VARCHAR(256) NULL,
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
    quantityCheckedIn INT UNSIGNED NOT NULL DEFAULT 0,
    price DOUBLE UNSIGNED NOT NULL DEFAULT 0,
    extendedPrice DOUBLE UNSIGNED NOT NULL DEFAULT 0,
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
DROP PROCEDURE IF EXISTS sp_Lookups_SelectAll;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Lookups_SelectAll ()
BEGIN
   	SELECT *
	FROM lookups;
END;;

DROP PROCEDURE IF EXISTS sp_Lookups_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Lookups_Select (
	IN listName VARCHAR(32)
)
BEGIN
	SELECT *
	FROM lookups
	WHERE lookups.listName = listName;
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
	DELETE FROM lookups
	WHERE lookups.listName = listName;
END;;

/*suppliers*/
DROP PROCEDURE IF EXISTS sp_Suppliers_SelectAll;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Suppliers_SelectAll ()
BEGIN
	SELECT *
	FROM suppliers;
END;;

DROP PROCEDURE IF EXISTS sp_Suppliers_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Suppliers_Select (
	IN supplierId INT UNSIGNED
)
BEGIN
	SELECT *
	FROM suppliers
	WHERE suppliers.supplierId = supplierId;
END;;

DROP PROCEDURE IF EXISTS sp_Suppliers_Insert;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Suppliers_Insert (
    OUT supplierId INT UNSIGNED,
	IN nickname VARCHAR(32),
    IN url VARCHAR(256)
)
BEGIN
	INSERT INTO suppliers(nickname, url)
	VALUES(nickname, url);
    
    SET supplierId = LAST_INSERT_ID();
END;;

DROP PROCEDURE IF EXISTS sp_Suppliers_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Suppliers_Update (
	IN supplierId INT UNSIGNED,
	IN nickname VARCHAR(32),
    IN url VARCHAR(256)
)
BEGIN
	UPDATE suppliers
    SET suppliers.nickname = nickname,
		suppliers.url = url
	WHERE suppliers.supplierId = supplierId;
END;;

DROP PROCEDURE IF EXISTS sp_Suppliers_Delete;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Suppliers_Delete (
	IN supplierId INT UNSIGNED
)
BEGIN
	DELETE FROM suppliers
	WHERE suppliers.supplierId = supplierId;
END;;

/*orders*/
DROP PROCEDURE IF EXISTS sp_Orders_SelectAll;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Orders_SelectAll ()
BEGIN
	SELECT *
	FROM orders;
END;;

DROP PROCEDURE IF EXISTS sp_Orders_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Orders_Select (
	IN orderId INT UNSIGNED
)
BEGIN
	SELECT *
	FROM orders
	WHERE orders.orderId = orderId;
END;;

DROP PROCEDURE IF EXISTS sp_Orders_Insert;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Orders_Insert (
    OUT orderId INT UNSIGNED,
	IN description VARCHAR (64),
	IN orderedFrom INT UNSIGNED,
    IN orderStatus VARCHAR (32),
    IN dateOrdered DATE,
    IN dateExpected DATE
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
	DELETE FROM orders
	WHERE orders.orderId = orderId;
END;;

/*orderItems*/
DROP PROCEDURE IF EXISTS sp_OrderItems_SelectAll;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_OrderItems_SelectAll ()
BEGIN
	SELECT orderItems.orderItemId, orderItems.orderId, orderItems.itemId,
		orderItems.quantityOrdered, orderItems.quantityCheckedIn, 
        orderItems.price, orderItems.extendedPrice,
		items.description, items.sku, items.sizeUnit, items.itemStatus
	FROM orderItems
		INNER JOIN items ON orderItems.itemId = items.itemId;
END;;

DROP PROCEDURE IF EXISTS sp_OrderItems_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_OrderItems_Select (
	IN orderItemId INT UNSIGNED
)
BEGIN
	SELECT orderItems.orderItemId, orderItems.orderId, orderItems.itemId,
		orderItems.quantityOrdered, orderItems.quantityCheckedIn,
        orderItems.price, orderItems.extendedPrice,
		items.description, items.sku, items.sizeUnit, items.itemStatus 
	FROM orderItems
		INNER JOIN items ON orderItems.itemId = items.itemId
	WHERE orderitems.orderItemId = orderItemId;
END;;

DROP PROCEDURE IF EXISTS sp_OrderItems_Insert;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_OrderItems_Insert (
	OUT orderItemId INT UNSIGNED,
	IN orderId INT UNSIGNED,
    IN itemId INT UNSIGNED,
    IN quantityOrdered INT UNSIGNED,
    IN price DOUBLE UNSIGNED
)
BEGIN
	DECLARE quantityCheckedIn INT UNSIGNED;
    DECLARE extendedPrice DOUBLE UNSIGNED;
    
    SET quantityCheckedIn = 0;
    SET extendedPrice = (quantityOrdered * price);
    
	INSERT INTO orderItems (orderId, itemId, quantityOrdered, quantityCheckedIn, price, extendedPrice)
    VALUES (orderId, itemId, quantityOrdered, quantityCheckedIn, price, extendedPrice);
	SET orderItemId = LAST_INSERT_ID(); 
END;;

DROP PROCEDURE IF EXISTS sp_OrderItems_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_OrderItems_Update (
	IN orderItemId INT UNSIGNED,
	IN quantityOrdered INT UNSIGNED,
    IN quantityCheckedIn INT UNSIGNED,
    IN price DOUBLE UNSIGNED
)
BEGIN
    DECLARE extendedPrice DOUBLE UNSIGNED;
    
    SET extendedPrice = (quantityOrdered * price);

	UPDATE orderitems
	SET	orderitems.quantityOrdered = quantityOrdered,
		orderitems.price = price,
        orderItems.quantityCheckedIn = quantityCheckedIn,
		orderitems.extendedPrice = extendedPrice
	WHERE
		orderitems.orderItemId = orderItemId;
END;;

DROP PROCEDURE IF EXISTS sp_OrderItems_Delete;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_OrderItems_Delete (
	IN orderItemId INT UNSIGNED
)
BEGIN
	DELETE FROM orderitems
	WHERE orderitems.orderItemId = orderItemId;
END;;

/*inventoryItems*/
DROP PROCEDURE IF EXISTS sp_InventoryItems_SelectAll;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_InventoryItems_SelectAll ()
BEGIN
	SELECT inventoryitems.inventoryItemId, inventoryitems.itemId,
		inventoryitems.quantity, inventoryitems.expirationDate,
		items.description, items.sku, items.sizeUnit, items.itemStatus 
	FROM inventoryItems
		INNER JOIN items ON inventoryItems.itemId = items.itemId;
END;;

DROP PROCEDURE IF EXISTS sp_InventoryItems_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_InventoryItems_Select (
	IN inventoryItemId INT UNSIGNED
)
BEGIN
	SELECT inventoryitems.inventoryItemId, inventoryitems.itemId,
		inventoryitems.quantity, inventoryitems.expirationDate,
		items.description, items.sku, items.sizeUnit, items.itemStatus 
	FROM inventoryItems
		INNER JOIN items ON inventoryItems.itemId = items.itemId
	WHERE InventoryItems.InventoryItemId = inventoryItemId;
END;;

DROP PROCEDURE IF EXISTS sp_InventoryItems_Insert;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_InventoryItems_Insert (
    OUT inventoryItemId INT UNSIGNED,
    IN quantity INT UNSIGNED,
    IN expirationDate DATE,
	IN description VARCHAR(64),
    IN sku VARCHAR(32),
    IN sizeUnit VARCHAR(32),
    IN itemStatus VARCHAR(32)
)
BEGIN
	DECLARE itemId INT UNSIGNED;
    
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
		RESIGNAL;
    END;
    
	START TRANSACTION;
	
	INSERT INTO items (description, sku, sizeUnit, itemStatus)
    VALUES (description, sku, sizeUnit, itemStatus);
    SET itemId = LAST_INSERT_ID();
    
    INSERT INTO inventoryItems(itemId, quantity, expirationDate)
	VALUES(itemId, quantity, expirationDate);
    SET inventoryItemId = LAST_INSERT_ID();

	COMMIT WORK;
END;;

DROP PROCEDURE IF EXISTS sp_InventoryItems_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_InventoryItems_Update (
	IN inventoryItemId INT UNSIGNED,
    IN quantity INT UNSIGNED,
    IN expirationDate DATE,
	IN description VARCHAR(64),
    IN sku VARCHAR(32),
    IN sizeUnit VARCHAR(32),
    IN itemStatus VARCHAR(32)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
	   RESIGNAL;
    END;
    
	START TRANSACTION;
	
	UPDATE items
    SET items.description = description,
		items.sku = sku,
	   items.sizeUnit = sizeUnit,
	   items.itemStatus = itemStatus
	WHERE items.itemId = (
		SELECT inventoryitems.itemId
		FROM inventoryitems
		WHERE inventoryitems.inventoryItemId = inventoryItemId);

	UPDATE InventoryItems
    SET inventoryItems.itemId = itemId,
		inventoryItems.quantity = quantity,
	   inventoryItems.expirationDate = expirationDate
	WHERE inventoryItems.inventoryItemId = inventoryItemId;

	COMMIT WORK;
END;;

DROP PROCEDURE IF EXISTS sp_InventoryItems_Delete;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_InventoryItems_Delete (
	IN inventoryItemId INT UNSIGNED
)
BEGIN
	DECLARE itemId INT UNSIGNED;

	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
	   RESIGNAL;
    END;
    
	START TRANSACTION;
	
    SET itemId = (
		SELECT inventoryitems.itemId
		FROM inventoryitems
		WHERE inventoryitems.inventoryItemId = inventoryItemId);
		  
	DELETE FROM inventoryItems
	WHERE inventoryItems.inventoryItemId = inventoryItemId;

	DELETE FROM items
	WHERE items.itemId = itemId
		AND items.ItemId NOT IN (
			SELECT orderitems.itemId
			FROM orderitems);

	COMMIT WORK;
END;;

/***********************************************************************
	Default Data
***********************************************************************/

DELIMITER ;

/*lookups*/
INSERT INTO lookups (listName, listValue) VALUES ('orderStatuses', 'Created');
INSERT INTO lookups (listName, listValue) VALUES ('orderStatuses', 'Ordered');
INSERT INTO lookups (listName, listValue) VALUES ('orderStatuses', 'Shipped');
INSERT INTO lookups (listName, listValue) VALUES ('orderStatuses', 'Delivered');
INSERT INTO lookups (listName, listValue) VALUES ('itemStatuses', 'Available');
INSERT INTO lookups (listName, listValue) VALUES ('itemStatuses', 'Discontinued');
INSERT INTO lookups (listName, listValue) VALUES ('itemStatuses', 'Do Not Order');

/*suppliers*/
INSERT INTO suppliers (nickname, url) VALUES ('Amazon', 'https://www.amazon.com/');
INSERT INTO suppliers (nickname, url) VALUES ('Wal-Mart', 'https://www.walmart.com/');
INSERT INTO suppliers (nickname, url) VALUES ('Target', 'https://www.target.com/');
