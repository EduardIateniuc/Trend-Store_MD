-- Indexes for categories table
ALTER TABLE categories ADD INDEX idx_categories_gender (gender);

-- Indexes for products table
ALTER TABLE products ADD INDEX idx_products_subcategory_price (subcategory_id, price);
ALTER TABLE products ADD INDEX idx_products_name_price (product_name, price);
ALTER TABLE products ADD INDEX idx_products_created (created_at);

-- Indexes for product_variants table
ALTER TABLE product_variants ADD INDEX idx_variants_product (product_id);
ALTER TABLE product_variants ADD INDEX idx_variants_stock (stock_level);
ALTER TABLE product_variants ADD INDEX idx_variants_size_color (size, color);
ALTER TABLE product_variants ADD INDEX idx_variants_composite (product_id, size, color, stock_level);

ALTER TABLE products ADD FULLTEXT INDEX idx_products_search (product_name, description);