CREATE TABLE company(
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE product_owner(
id INT NOT NULL AUTO_INCREMENT,
first_name VARCHAR(40) NOT NULL,
last_name VARCHAR(40) NOT NULL,
company_id INT NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (company_id) REFERENCES warehouse.company(id)
);

CREATE TABLE contact(
id INT NOT NULL AUTO_INCREMENT,
access_modifier VARCHAR(20) NOT NULL,
type VARCHAR(20) NOT NULL,
value VARCHAR(50) NOT NULL,
product_owner_id INT NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (product_owner_id) REFERENCES warehouse.product_owner(id)
);

CREATE TABLE product_category(
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(20) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE item(
id INT NOT NULL AUTO_INCREMENT,
title VARCHAR(50),
count INT NOT NULL,
price FLOAT NOT NULL,
storing_date DATETIME NOt NULL,
product_owner_id INT NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (product_owner_id) REFERENCES warehouse.product_owner(id)
);

CREATE TABLE item_product_category(
item_id INT NOT NULL,
product_category_id INT NOT NULL,
PRIMARY KEY (item_id, product_category_id),
FOREIGN KEY (item_id) REFERENCES warehouse.item(id) ON DELETE CASCADE,
FOREIGN KEY (product_category_id) REFERENCES warehouse.product_category(id) ON DELETE CASCADE
);