-- ==========================================================
-- Restaurant Management System - Database Schema
-- ==========================================================

-- Table 1: Dining Tables
CREATE TABLE tables (
    id INT PRIMARY KEY,
    capacity INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'free'
);

-- Seed data for dining tables
INSERT INTO tables (id, capacity, status) VALUES
(1, 5, 'free'),
(2, 4, 'free'),
(3, 7, 'free');

-- Table 2: Orders
CREATE TABLE orders (
    id INT PRIMARY KEY,
    table_id INT NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    total_amount DOUBLE PRECISION NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Pending',
    FOREIGN KEY (table_id) REFERENCES tables(id) ON DELETE CASCADE
);

-- Seed data for orders
INSERT INTO orders (id, table_id, customer_name, total_amount, status) VALUES
(101, 1, 'John Doe', 25.50, 'Pending'),
(102, 2, 'Alice Smith', 42.00, 'Completed');

-- Table 3: Menu Items
CREATE TABLE menu_items (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION NOT NULL
);

-- Seed data for menu items (Idli, Dosa, Pongal, Biriyani)
INSERT INTO menu_items (id, name, price) VALUES
(1, 'Idli', 30.00),
(2, 'Dosa', 50.00),
(3, 'Pongal', 45.00),
(4, 'Biriyani', 120.00);

-- Table 4: Bills (Generated Customer Bills)
CREATE TABLE bills (
    bill_no INT PRIMARY KEY,
    bill_date VARCHAR(20) NOT NULL,
    grand_total DOUBLE PRECISION NOT NULL
);

-- Table 5: Bill Line Items
CREATE TABLE bill_items (
    id SERIAL PRIMARY KEY,
    bill_no INT NOT NULL,
    item_id INT NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    quantity INT NOT NULL,
    subtotal DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (bill_no) REFERENCES bills(bill_no) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES menu_items(id)
);
