-- Adding product variants with different sizes and colors
-- Each variant includes stock level information
INSERT INTO product_variants (product_id, size, color, stock_level) VALUES
-- Variants for Classic Cotton T-Shirt (product_id = 1)
(1, 'S', 'White', 100),
(1, 'M', 'White', 150),
(1, 'L', 'White', 100),
(1, 'XL', 'White', 50),
(1, 'S', 'Black', 100),
(1, 'M', 'Black', 150),
(1, 'L', 'Black', 100),
(1, 'XL', 'Black', 50),
(1, 'S', 'Navy', 75),
(1, 'M', 'Navy', 100),
(1, 'L', 'Navy', 75),
(1, 'XL', 'Navy', 25),

-- Variants for Graphic Print T-Shirt (product_id = 2)
(2, 'S', 'Grey', 50),
(2, 'M', 'Grey', 75),
(2, 'L', 'Grey', 50),
(2, 'XL', 'Grey', 25),

-- Variants for Slim Fit Denim Jeans (product_id = 3)
(3, '30x32', 'Blue', 30),
(3, '32x32', 'Blue', 40),
(3, '34x32', 'Blue', 30),
(3, '36x32', 'Blue', 20),
(3, '30x32', 'Black', 30),
(3, '32x32', 'Black', 40),
(3, '34x32', 'Black', 30),
(3, '36x32', 'Black', 20),

-- Variants for Urban Sport Sneakers (product_id = 4)
(4, '7', 'Black/White', 25),
(4, '8', 'Black/White', 30),
(4, '9', 'Black/White', 35),
(4, '10', 'Black/White', 30),
(4, '11', 'Black/White', 25),
(4, '7', 'Grey/White', 25),
(4, '8', 'Grey/White', 30),
(4, '9', 'Grey/White', 35),
(4, '10', 'Grey/White', 30),
(4, '11', 'Grey/White', 25);