-- Order Items Table
CREATE TABLE order_items (
                             order_item_id INT AUTO_INCREMENT PRIMARY KEY,
                             order_id INT,
                             product_variant_id BIGINT,
                             quantity INT NOT NULL CHECK ( quantity > 0 ),
                             product_price DECIMAL(10, 2) NOT NULL,  -- Price at the time of order
                             FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
                             FOREIGN KEY (product_variant_id) REFERENCES product_variants(id)
);