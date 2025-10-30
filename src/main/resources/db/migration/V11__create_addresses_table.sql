-- Addresses Table
CREATE TABLE addresses (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           customer_id INT,
                           address_line_1 TEXT NOT NULL,
                           address_line_2 TEXT,
                           postal_code VARCHAR(20) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);
