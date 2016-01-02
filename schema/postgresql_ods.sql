----------------------------------------------------------------------------------
-- Main Table
-- 
----------------------------------------------------------------------------------
DROP TABLE IF EXISTS product_specification CASCADE;
DROP TABLE IF EXISTS product CASCADE;

----------------------------------------------------------------------------------
-- TABLE product
-- Create Table product to store product mapping information
----------------------------------------------------------------------------------
CREATE TABLE product
(
product_serial_number VARCHAR(200) NOT NULL UNIQUE,
product_name VARCHAR(4000) UNIQUE,
product_description VARCHAR(4000),
product_price float8 NOT NULL,
product_price_unit VARCHAR(10) NOT NULL,
PRIMARY KEY (product_serial_number)
);  

----------------------------------------------------------------------------------
-- TABLE product_specification
-- Create Table product_specification to store product specification mapping information
----------------------------------------------------------------------------------
CREATE TABLE product_specification
(
product_serial_number VARCHAR(200) NOT NULL UNIQUE,
product_specification_name VARCHAR(4000) UNIQUE,
product_specification_value VARCHAR(200) UNIQUE,
FOREIGN KEY (product_serial_number) REFERENCES product (product_serial_number)
);  

----------------------------------------------------------------------------------
-- Sample
-- Comment out as necessary
----------------------------------------------------------------------------------
INSERT INTO product (product_serial_number, product_name, product_description, product_price, product_price_unit) VALUES ('ua-100', 'unique Audio Power Noise Cancellator', 'nique Audio Power Noise Cancellator Donec id elit non mi porta gravida at eget metus. Fuscedapibus, tellus ac cursus commodo, tortor mauris condimentumnibh, ut fermentum massa justo sit amet risus. Etiam porta semmalesuada magna mollis euismod. Donec sed odio dui.', 200.00, 'USD');
INSERT INTO product (product_serial_number, product_specification_name, product_specification_value) VALUES ('ua-100', 'Dimension', '2 in x 2 in x 2 in');
INSERT INTO product (product_serial_number, product_specification_name, product_specification_value) VALUES ('ua-100', 'Weight', '1 lb');
INSERT INTO product (product_serial_number, product_specification_name, product_specification_value) VALUES ('ua-100', 'Image', 'img/testimage.jpg');

INSERT INTO product (product_serial_number, product_name, product_description, product_price, product_price_unit) VALUES ('ua-200', 'Unique Audio Power Cable Eliminater Donec id elit non mi porta gravida at eget metus. Fuscedapibus, tellus ac cursus commodo, tortor mauris condimentumnibh, ut fermentum massa justo sit amet risus. Etiam porta semmalesuada magna mollis euismod. Donec sed odio dui.', 100.00, 'USD');
INSERT INTO product (product_serial_number, product_specification_name, product_specification_value) VALUES ('ua-200', 'Length', '2 in');
INSERT INTO product (product_serial_number, product_specification_name, product_specification_value) VALUES ('ua-200', 'Weight', '2 lb');
INSERT INTO product (product_serial_number, product_specification_name, product_specification_value) VALUES ('ua-200', 'Input', 'US Standard Power Port');
INSERT INTO product (product_serial_number, product_specification_name, product_specification_value) VALUES ('ua-200', 'Image', 'img/testimage.jpg');
