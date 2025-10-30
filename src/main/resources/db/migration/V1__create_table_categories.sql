CREATE TABLE categories (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            category_name VARCHAR(100) NOT NULL,
                            gender VARCHAR(20),
                            image_url VARCHAR(512),
                            display_order INT,
                            visible BOOLEAN DEFAULT TRUE,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
-- Subcategories Table
-- When a category is deleted, all its subcategories should be removed
CREATE TABLE subcategories (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               subcategory_name VARCHAR(100) NOT NULL,
                               category_id INTEGER NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               CONSTRAINT fk_category
                                   FOREIGN KEY (category_id)
                                       REFERENCES categories(id)
                                       ON DELETE CASCADE
                                       ON UPDATE CASCADE,
                               CONSTRAINT unique_subcategory_name_per_category
                                   UNIQUE (category_id, subcategory_name)
);