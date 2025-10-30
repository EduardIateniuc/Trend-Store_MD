-- Customers Table
CREATE TABLE customers (
                           customer_id INT AUTO_INCREMENT PRIMARY KEY,
                           first_name VARCHAR(50) NOT NULL,
                           last_name VARCHAR(50) NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           phone_number VARCHAR(15),
                           password_hash VARCHAR(255) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);