-- Orders Table
CREATE TABLE orders (
                        order_id INT AUTO_INCREMENT PRIMARY KEY,
                        customer_id INT,
                        order_status VARCHAR(50) NOT NULL,
                        shipping_address_id INT,
                        shipping_method_id INT,
                        payment_method_id INT,
                        order_total DECIMAL(10, 2) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
                        FOREIGN KEY (shipping_address_id) REFERENCES addresses(id) ON DELETE SET NULL,
                        FOREIGN KEY (shipping_method_id) REFERENCES shipping_methods(shipping_method_id),
                        FOREIGN KEY (payment_method_id) REFERENCES payment_methods(payment_method_id)
);