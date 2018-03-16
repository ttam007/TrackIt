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
DROP TABLE IF EXISTS lookups;

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

CREATE TABLE orders (
    orderId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    description VARCHAR (64) NOT NULL,
    orderedFrom VARCHAR (32) NOT NULL,
    dateOrdered DATE NOT NULL,
    orderStatus VARCHAR (16) NOT NULL,
    dateExpected DATE NULL,
    PRIMARY KEY (orderId),
    UNIQUE idx_orders_orderId(orderId)
    );

CREATE TABLE items (
    itemId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    description VARCHAR (64) NOT NULL,
    sku VARCHAR(32) NULL,
    size FLOAT(6,2) UNSIGNED NULL,
    sizeUnit VARCHAR(32) NULL,
    isHidden BIT NOT NULL DEFAULT 0,
    PRIMARY KEY (itemId),
    UNIQUE idx_items_itemId(itemId),
    CONSTRAINT fk_items_lookups FOREIGN KEY (sizeUnit) REFERENCES lookups(listValue)
    );
    
CREATE TABLE orderItems (
    orderItemId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    orderId INT UNSIGNED NOT NULL,
    itemId INT UNSIGNED NOT NULL,
    quantityOrdered INT UNSIGNED NOT NULL DEFAULT 1,
    PRIMARY KEY (orderItemId),
    UNIQUE idx_orderItems_orderItemId (orderItemId),
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
    UNIQUE idx_inventory_inventoryId (inventoryId),
    CONSTRAINT fk_inventory_items FOREIGN KEY (itemId)
        REFERENCES items (itemId)
);

/***********************************************************************
	Stored Procedures
***********************************************************************/

DELIMITER ;;

/*items*/
DROP PROCEDURE IF EXISTS sp_Items_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Items_Select (
	IN itemId INT
)
BEGIN
	IF (itemID IS NULL) THEN
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
    IN size FLOAT(6,2),
    IN sizeUnit VARCHAR(8)
)
BEGIN
	INSERT INTO items (description, sku, size, sizeUnit)
    VALUES (description, sku, size, sizeUnit);
END;;

DROP PROCEDURE IF EXISTS sp_Items_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Items_Update (
	IN itemId INT,
	IN description VARCHAR(64),
    IN sku VARCHAR(32),
    IN size FLOAT(6,2),
    IN sizeUnit VARCHAR(8)
)
BEGIN
	UPDATE items
    SET items.description = description,
		items.sku = sku,
        items.size = size,
        items.sizeUnit = sizeUnit
	WHERE items.itemId = itemId;
END;;

DROP PROCEDURE IF EXISTS sp_Items_Delete;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_Items_Delete (
	IN itemId INT
)
BEGIN
	DELETE FROM items
    WHERE items.itemId = itemId;
END;;

/*orderItems*/
DROP PROCEDURE IF EXISTS sp_OrderItems_Select;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_OrderItems_Select (
	IN orderItemId INT
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
	IN orderId INT,
    IN itemId INT,
    IN quantityOrdered INT
)
BEGIN
	INSERT INTO orderItems (orderId, itemId, quantityOrdered)
    VALUES (orderId, itemId, quantityOrdered);
END;;

DROP PROCEDURE IF EXISTS sp_OrderItems_Update;;
CREATE DEFINER = CURRENT_USER 
PROCEDURE sp_OrderItems_Update (
	IN orderItemId INT,
	IN orderId INT,
    IN itemId INT,
    IN quantityOrdered INT
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
	IN orderItemId INT
)
BEGIN
	DELETE FROM orderitems
    WHERE orderitems.orderItemId = orderItemId;
END;;

/***********************************************************************
	Default Data
***********************************************************************/

DELIMITER ;

/*lookups table*/
INSERT INTO lookups (listName, listValue) VALUES ('sizeUnits', 'Large');
INSERT INTO lookups (listName, listValue) VALUES ('sizeUnits', 'Medium');
INSERT INTO lookups (listName, listValue) VALUES ('sizeUnits', 'Small');
INSERT INTO lookups (listName, listValue) VALUES ('sizeUnits', 'ounce');
INSERT INTO lookups (listName, listValue) VALUES ('sizeUnits', 'quart');
INSERT INTO lookups (listName, listValue) VALUES ('sizeUnits', 'gallon');
