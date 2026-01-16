-- This is the complete and final script to set up your database.
-- Run this entire script in MySQL Workbench.

-- 1. Drop the existing database to start fresh (optional, but recommended for a clean setup)
DROP DATABASE IF EXISTS inventory_management;

-- 2. Create and select the new database
CREATE DATABASE inventory_management;
USE inventory_management;

-- 3. Create all necessary tables with consistent naming and constraints

CREATE TABLE employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100),
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    phone_number VARCHAR(20),
    salary DOUBLE,
    status VARCHAR(20) NOT NULL,
    photo_url VARCHAR(255) DEFAULT NULL
);

CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE brands (
    brand_id INT AUTO_INCREMENT PRIMARY KEY,
    brand_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(150) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    category_id INT,
    brand_id INT,
    status VARCHAR(50) NOT NULL,
    image_path VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    FOREIGN KEY (brand_id) REFERENCES brands(brand_id)
);

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    customer_name VARCHAR(255),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price_per_unit DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE purchase_orders (
    purchase_order_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    employee_id INT,
    quantity_ordered INT NOT NULL,
    supplier_name VARCHAR(255),
    order_date DATE NOT NULL,
    expected_delivery DATE,
    status VARCHAR(50) DEFAULT 'Pending',
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);


-- 4. Insert sample data to populate the system

INSERT INTO employees (username, name, email, password, role, status, salary, photo_url) VALUES
('admin', 'Admin User', 'admin@example.com', 'admin123', 'Admin', 'Active', 75000, 'https://i.pravatar.cc/150?u=admin'),
('employee', 'Saksham Talwar', 'employee@example.com', 'employee123', 'Employee', 'Active', 50000, 'https://i.pravatar.cc/150?u=employee');

INSERT INTO categories (category_name) VALUES ('Fruits'), ('Dairy'), ('Bakery'), ('Vegetables');
INSERT INTO brands (brand_name) VALUES ('FarmFresh'), ('DairyLand'), ('Bakery Co.'), ('Nestle');

INSERT INTO products (product_name, description, price, quantity, category_id, brand_id, status) VALUES
('Apple', 'Fresh red apples', 150.00, 100, 1, 1, 'Available'),
('Milk (1L)', 'Full cream milk', 65.00, 50, 2, 2, 'Available'),
('Whole Wheat Bread', 'Soft whole wheat bread', 45.00, 30, 3, 3, 'Available'),
('Coffee', 'Instant coffee powder', 500.00, 45, 2, 4, 'Available');

INSERT INTO orders (employee_id, total_amount, customer_name) VALUES (2, 1250.00, 'John Doe');
INSERT INTO order_items (order_id, product_id, quantity, price_per_unit) VALUES (1, 1, 5, 150.00);
INSERT INTO order_items (order_id, product_id, quantity, price_per_unit) VALUES (1, 2, 10, 50.00);

INSERT INTO purchase_orders (product_id, employee_id, quantity_ordered, supplier_name, order_date, expected_delivery, status) 
VALUES (2, 2, 100, 'DairyLand', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'Pending');

-- 5. Confirmation message
SELECT 'Database setup complete. All tables created and sample data inserted.' AS Status;







-- drop database inventory_management;

-- 1. Create and select the database
-- CREATE DATABASE IF NOT EXISTS inventory_management;
-- USE inventory_management;

-- 2. Drop existing tables to ensure a clean start
-- DROP TABLE IF EXISTS order_items, orders, products, employees, categories, brands;

-- 3. Create the main table for users, named 'employees' to match the Java code
-- CREATE TABLE employees (
--     employee_id INT AUTO_INCREMENT PRIMARY KEY,
--     username VARCHAR(50) NOT NULL UNIQUE,
--     name VARCHAR(100),
--     email VARCHAR(100) NOT NULL UNIQUE,
--     password VARCHAR(255) NOT NULL,       -- Storing plaintext as requested
--     role VARCHAR(20) NOT NULL,             -- 'Admin' or 'Employee'
--     phone_number VARCHAR(20),
--     salary DOUBLE,
--     status VARCHAR(20) NOT NULL
-- );

-- 4. Create supporting tables for products
-- CREATE TABLE categories (
--     category_id INT AUTO_INCREMENT PRIMARY KEY,
--     category_name VARCHAR(255) NOT NULL UNIQUE
-- );

-- CREATE TABLE brands (
--     brand_id INT AUTO_INCREMENT PRIMARY KEY,
--     brand_name VARCHAR(255) NOT NULL UNIQUE
-- );

-- CREATE TABLE products (
--     product_id INT AUTO_INCREMENT PRIMARY KEY,
--     product_name VARCHAR(255) NOT NULL,
--     description TEXT,
--     price DECIMAL(10, 2) NOT NULL,
--     quantity INT NOT NULL DEFAULT 0,
--     category_id INT,
--     brand_id INT,
--     image_path VARCHAR(500),
--     status VARCHAR(50) DEFAULT 'Available',
--     FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL,
--     FOREIGN KEY (brand_id) REFERENCES brands(brand_id) ON DELETE SET NULL
-- );


-- 5. Insert sample data
-- Default passwords are 'admin123' and 'employee123'
-- INSERT INTO employees (username, name, email, password, role, status) VALUES
-- ('admin', 'Admin User', 'admin@example.com', 'admin123', 'Admin', 'Active'),
-- ('employee', 'Employee User', 'employee@example.com', 'employee123', 'Employee', 'Active');

-- INSERT INTO categories (category_name) VALUES
-- ('Fruits'), ('Vegetables'), ('Dairy'), ('Bakery'), ('Meat');

-- INSERT INTO brands (brand_name) VALUES
-- ('Local Farm'), ('Nestle'), ('Amul'), ('FreshBake');

-- Insert sample products using the categories and brands created above
-- INSERT INTO products (product_name, description, price, quantity, category_id, brand_id) VALUES
-- ('Apple (1kg)', 'Fresh red apples', 2.99, 100, 1, 1),
-- ('Amul Milk (1L)', 'Full cream milk', 1.50, 50, 3, 3),
-- ('Whole Wheat Bread', 'Soft whole wheat bread', 2.25, 30, 4, 4),
-- ('Local Eggs (Dozen)', 'Farm fresh brown eggs', 3.75, 40, 3, 1);

-- Confirmation message
-- SELECT 'Database setup complete. Tables created and sample data inserted.' AS status;
-- SET SQL_SAFE_UPDATES = 0;
-- UPDATE employees SET username = TRIM(username), password = TRIM(password);

-- 	UPDATE employees SET 
-- 	username = TRIM(username), 
-- 	password = TRIM(password);
--     
--     
--     -- Create the categories table
-- drop table categories;
-- CREATE TABLE IF NOT EXISTS categories (
--     category_id INT AUTO_INCREMENT PRIMARY KEY,
--     category_name VARCHAR(100) NOT NULL UNIQUE
-- );

-- Create the brands table
-- CREATE TABLE IF NOT EXISTS brands (
--     brand_id INT AUTO_INCREMENT PRIMARY KEY,
--     brand_name VARCHAR(100) NOT NULL UNIQUE
-- );

-- Create a more robust products table with foreign keys
-- CREATE TABLE IF NOT EXISTS products (
--     product_id INT AUTO_INCREMENT PRIMARY KEY,
--     product_name VARCHAR(150) NOT NULL,
--     description TEXT,
--     price DECIMAL(10, 2) NOT NULL,
--     quantity INT NOT NULL,
--     category_id INT,
--     brand_id INT,
--     status VARCHAR(50) NOT NULL,
--     image_path VARCHAR(255),
--     FOREIGN KEY (category_id) REFERENCES categories(category_id),
--     FOREIGN KEY (brand_id) REFERENCES brands(brand_id)
-- );
-- use inventory_management;
-- Optional: Insert some sample data to start
-- INSERT INTO categories (category_name) VALUES ('Fruits'), ('Dairy'), ('Bakery');
-- INSERT INTO brands (brand_name) VALUES ('FarmFresh'), ('DairyLand'), ('Bakery Co.');

-- ALTER TABLE employees
-- ADD COLUMN photo_url VARCHAR(255) DEFAULT NULL;


-- Table to store overall order information
-- CREATE TABLE IF NOT EXISTS orders (
--     order_id INT AUTO_INCREMENT PRIMARY KEY,
--     employee_id INT,
--     order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
--     total_amount DECIMAL(10, 2) NOT NULL,
--     customer_name VARCHAR(	255),
--     FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
-- );

-- Table to store individual items within each order
-- CREATE TABLE IF NOT EXISTS order_items (
--     order_item_id INT AUTO_INCREMENT PRIMARY KEY,
--     order_id INT NOT NULL,
--     product_id INT NOT NULL,
--     quantity INT NOT NULL,
--     price_per_unit DECIMAL(10, 2) NOT NULL,
--     FOREIGN KEY (order_id) REFERENCES orders(order_id),
--     FOREIGN KEY (product_id) REFERENCES products(product_id)
-- );

-- Insert some sample data for demonstration
-- INSERT INTO orders (order_id, employee_id, total_amount, customer_name) VALUES (1, 2, 23.45, 'John Doe');
-- INSERT INTO order_items (order_id, product_id, quantity, price_per_unit) VALUES (1, 1, 5, 2.99);
-- INSERT INTO order_items (order_id, product_id, quantity, price_per_unit) VALUES (1, 3, 2, 4.25);


