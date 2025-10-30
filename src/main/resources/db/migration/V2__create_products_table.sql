-- Products Table
-- When a subcategory is deleted, all its products should be removed
CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          product_name VARCHAR(255) NOT NULL,
                          description TEXT,
                          price DECIMAL(10,2) NOT NULL,
                          subcategory_id BIGINT NOT NULL,
    -- Using JSON array to store multiple image URLs
                          images JSON COMMENT 'Array of product image URLs, first image is considered primary',
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT fk_subcategory
                              FOREIGN KEY (subcategory_id)
                                  REFERENCES subcategories(id)
                                  ON DELETE CASCADE
                                  ON UPDATE CASCADE,
                          CONSTRAINT check_price_positive
                              CHECK (price >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;