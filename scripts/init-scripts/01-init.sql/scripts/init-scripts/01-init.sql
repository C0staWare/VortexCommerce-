CREATE DATABASE products_db;
\c products_db;

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(19,2) NOT NULL,
    stock INTEGER NOT NULL
);

INSERT INTO products (name, description, price, stock) VALUES
('Laptop Gamer', 'RTX 4080, 32GB RAM', 2500.00, 10),
('Monitor 4K', '32 inch IPS', 600.00, 20);