-- Shipping Methods Table
CREATE TABLE shipping_methods (
                                  shipping_method_id INT AUTO_INCREMENT PRIMARY KEY,
                                  method_name VARCHAR(50) NOT NULL,
                                  cost DECIMAL(10, 2) NOT NULL,
                                  delivery_speed VARCHAR(50),
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);