-- Now we add subcategories, linking them to their parent categories
-- We use the category IDs created in the previous migration
INSERT INTO subcategories (subcategory_name, category_id) VALUES
-- Clothing subcategories (category_id = 1)
('T-Shirts', 1),
('Jeans', 1),
('Hoodies', 1),
('Jackets', 1),
('Dresses', 1),

-- Shoes subcategories (category_id = 2)
('Sneakers', 2),
('Boots', 2),
('Sandals', 2),
('Athletic', 2),
('Formal', 2),

-- Accessories subcategories (category_id = 3)
('Bags', 3),
('Jewelry', 3),
('Belts', 3),
('Hats', 3),
('Scarves', 3);