-- Payment Methods Table
CREATE TABLE payment_methods (
                                 payment_method_id INT AUTO_INCREMENT PRIMARY KEY,
                                 method_name VARCHAR(50) NOT NULL,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);