/***********************************************************************
	Header
***********************************************************************/

use TrackItDB;

/***********************************************************************
	Drops
***********************************************************************/

DELIMITER ;

/*lookups*/
ALTER TABLE items DROP FOREIGN KEY fk_items_lookups;
ALTER TABLE orders DROP FOREIGN KEY fk_orders_lookups;
DROP TABLE IF EXISTS lookups;

/*suppliers*/
ALTER TABLE orders DROP FOREIGN KEY fk_orders_suppliers;
DROP TABLE IF EXISTS suppliers;

/*orders*/
ALTER TABLE orderItems DROP FOREIGN KEY fk_orderItems_orders;
DROP TABLE IF EXISTS orders;

/*items*/
ALTER TABLE orderItems DROP FOREIGN KEY fk_orderItems_items;
ALTER TABLE inventory DROP FOREIGN KEY fk_inventory_items;
DROP TABLE IF EXISTS items;

/*orderItems*/
DROP TABLE IF EXISTS orderItems;

/*inventory*/
DROP TABLE IF EXISTS inventory;

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
    dateOrdered DATE NOT NULL,
    orderStatus VARCHAR (32) NOT NULL,
    dateExpected DATE NULL,
    PRIMARY KEY (orderId),
    CONSTRAINT fk_orders_suppliers FOREIGN KEY (orderedFrom) REFERENCES suppliers(supplierId),
    CONSTRAINT fk_orders_lookups FOREIGN KEY (orderStatus) REFERENCES lookups(listValue)
   );

CREATE TABLE items (
    itemId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    description VARCHAR (64) NOT NULL,
    sku VARCHAR(32) NULL,
    sizeAmount FLOAT(6,2) UNSIGNED NULL,
    sizeUnit VARCHAR(32) NULL,
    isHidden BIT NOT NULL DEFAULT 0,
    PRIMARY KEY (itemId),
    CONSTRAINT fk_items_lookups FOREIGN KEY (sizeUnit) REFERENCES lookups(listValue)
    );
    
CREATE TABLE orderItems (
    orderItemId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    orderId INT UNSIGNED NOT NULL,
    itemId INT UNSIGNED NOT NULL,
    quantityOrdered INT UNSIGNED NOT NULL DEFAULT 1,
    PRIMARY KEY (orderItemId),
    CONSTRAINT fk_orderItems_orders FOREIGN KEY (orderId)
        REFERENCES orders (orderId),
    CONSTRAINT fk_orderItems_items FOREIGN KEY (itemId)
        REFERENCES items (itemId)
);

CREATE TABLE inventory (
    inventoryId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    itemId INT UNSIGNED NOT NULL,
    quantity INT UNSIGNED NOT NULL DEFAULT 0,
    expirationDate DATE NULL,
    PRIMARY KEY (inventoryId),
    CONSTRAINT fk_inventory_items FOREIGN KEY (itemId)
        REFERENCES items (itemId)
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
	INSERT INTO suppliers(nickname,URL)
	VALUES(nickname,URL);
    
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
    IN dateOrdered DATE,
    IN orderStatus VARCHAR (32),
    IN dateExpected DATE,
    OUT orderId INT UNSIGNED
)
BEGIN
	INSERT INTO orders (description, orderedFrom, dateOrdered, orderStatus, dateExpected)
    VALUES (description, orderedFrom, dateOrdered, orderStatus, dateExpected);
    
    SET orderId = LAST_INSERT_ID();
END;;

DROP PROCEDURE IF EXISTS sp_Orders_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Orders_Update (
	IN orderId INT UNSIGNED,
	IN description VARCHAR (64),
    IN orderedFrom INT UNSIGNED,
    IN dateOrdered DATE,
    IN orderStatus VARCHAR (32),
    IN dateExpected DATE
)
BEGIN
	UPDATE orders
    SET orders.description = description,
		orders.orderedFrom = orderedFrom,
        orders.dateOrdered = dateOrdered,
        orders.orderStatus = orderStatus,
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
    IN sizeAmount FLOAT(6,2) UNSIGNED,
    IN sizeUnit VARCHAR(32),
    IN isHidden BIT,
    OUT itemId INT UNSIGNED
)
BEGIN
	INSERT INTO items (description, sku, sizeAmount, sizeUnit, isHidden)
    VALUES (description, sku, sizeAmount, sizeUnit, isHidden);
    
    SET itemId = LAST_INSERT_ID();
END;;

DROP PROCEDURE IF EXISTS sp_Items_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Items_Update (
	IN itemId INT UNSIGNED,
	IN description VARCHAR(64),
    IN sku VARCHAR(32),
    IN sizeAmount FLOAT(6,2) UNSIGNED,
    IN sizeUnit VARCHAR(32),
    IN isHidden BIT
)
BEGIN
	UPDATE items
    SET items.description = description,
		items.sku = sku,
        items.sizeAmount = sizeAmount,
        items.sizeUnit = sizeUnit,
        items.isHidden = isHidden
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
    OUT orderItemId INT UNSIGNED
)
BEGIN
	INSERT INTO orderItems (orderId, itemId, quantityOrdered)
    VALUES (orderId, itemId, quantityOrdered);
    
	SET orderItemId = LAST_INSERT_ID(); 
END;;

DROP PROCEDURE IF EXISTS sp_OrderItems_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_OrderItems_Update (
	IN orderItemId INT UNSIGNED,
	IN orderId INT UNSIGNED,
    IN itemId INT UNSIGNED,
    IN quantityOrdered INT UNSIGNED
)
BEGIN
	UPDATE orderitems
    SET orderitems.orderId = orderId,
		orderitems.itemId = itemId,
        orderitems.quantityOrdered = quantityOrdered        
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

/*inventory*/
DROP PROCEDURE IF EXISTS sp_Inventory_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Inventory_Select (
	IN inventoryId INT UNSIGNED
)
BEGIN
	IF (inventoryId IS NULL) THEN
    	SELECT *
		FROM inventory;
    ELSE
    	SELECT *
		FROM inventory
		WHERE inventory.inventoryId = inventoryId;
	END IF;
END;;

DROP PROCEDURE IF EXISTS sp_Inventory_Insert;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Inventory_Insert (
	IN itemId INT UNSIGNED,
    IN quantity INT UNSIGNED,
    IN expirationDate DATE,
    OUT inventoryId INT UNSIGNED
)
BEGIN
	INSERT INTO inventory(itemId, quantity, expirationDate)
	VALUES(itemId, quantity, expirationDate);
    
    SET inventoryId = LAST_INSERT_ID();
END;;

DROP PROCEDURE IF EXISTS sp_Inventory_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Inventory_Update (
	IN inventoryId INT UNSIGNED,
	IN itemId INT UNSIGNED,
    IN quantity INT UNSIGNED,
    IN expirationDate DATE
)
BEGIN
	UPDATE inventory
    SET inventory.itemId = itemId,
		inventory.quantity = quantity,
        inventory.expirationDate = expirationDate
	WHERE inventory.inventoryId = inventoryId;
END;;

DROP PROCEDURE IF EXISTS sp_Inventory_Delete;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Inventory_Delete (
	IN inventoryId INT UNSIGNED
)
BEGIN
	IF (inventoryId IS NULL) THEN
		DELETE FROM inventory;
	ELSE
		DELETE FROM inventory
		WHERE inventory.inventoryId = inventoryId;
	END IF;
END;;

/***********************************************************************
	Default Data
***********************************************************************/

DELIMITER ;

/*lookups*/
INSERT INTO lookups (listName, listValue) VALUES ('sizeUnits', 'Large');
INSERT INTO lookups (listName, listValue) VALUES ('sizeUnits', 'Medium');
INSERT INTO lookups (listName, listValue) VALUES ('sizeUnits', 'Small');
INSERT INTO lookups (listName, listValue) VALUES ('sizeUnits', 'ounce');
INSERT INTO lookups (listName, listValue) VALUES ('sizeUnits', 'quart');
INSERT INTO lookups (listName, listValue) VALUES ('sizeUnits', 'gallon');
INSERT INTO lookups (listName, listValue) VALUES ('orderStatuses', 'Ordered');
INSERT INTO lookups (listName, listValue) VALUES ('orderStatuses', 'Shipping');
INSERT INTO lookups (listName, listValue) VALUES ('orderStatuses', 'Arrived');

/*suppliers*/
INSERT INTO suppliers (nickname, URL) VALUES ('Amazon', 'https://www.amazon.com/');
INSERT INTO suppliers (nickname, URL) VALUES ('Wal-Mart', 'https://www.walmart.com/');
INSERT INTO suppliers (nickname, URL) VALUES ('Target', 'https://www.target.com/');
