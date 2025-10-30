-- Product Variants Table
-- When a product is deleted, all its variants should be removed
CREATE TABLE product_variants (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  product_id BIGINT NOT NULL,
                                  size VARCHAR(20) NOT NULL,
                                  color VARCHAR(50) NOT NULL,
                                  stock_level INTEGER NOT NULL DEFAULT 0,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  CONSTRAINT fk_product
                                      FOREIGN KEY (product_id)
                                          REFERENCES products(id)
                                          ON DELETE CASCADE
                                          ON UPDATE CASCADE,
                                  CONSTRAINT unique_product_size_color
                                      UNIQUE (product_id, size, color),
                                  CONSTRAINT check_stock_level_positive
                                      CHECK (stock_level >= 0)
);