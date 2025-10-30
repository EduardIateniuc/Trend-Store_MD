-- Adding sample products for each subcategory
-- Each product includes a description, price, and multiple product images
INSERT INTO products (product_name, description, price, subcategory_id, images) VALUES
-- T-Shirts (subcategory_id = 1)
('Classic Cotton T-Shirt',
 'Premium quality cotton t-shirt with a comfortable fit',
 29.99,
 1,
 JSON_ARRAY(
         'https://storage.trend-shop.com/products/tshirts/classic-cotton/front.jpg',
         'https://storage.trend-shop.com/products/tshirts/classic-cotton/back.jpg',
         'https://storage.trend-shop.com/products/tshirts/classic-cotton/detail.jpg'
 )),

('Graphic Print T-Shirt',
 'Urban style graphic t-shirt with artistic design',
 34.99,
 1,
 JSON_ARRAY(
         'https://storage.trend-shop.com/products/tshirts/graphic-print/front.jpg',
         'https://storage.trend-shop.com/products/tshirts/graphic-print/back.jpg'
 )),

-- Jeans (subcategory_id = 2)
('Slim Fit Denim Jeans',
 'Classic slim fit jeans with stretch comfort',
 79.99,
 2,
 JSON_ARRAY(
         'https://storage.trend-shop.com/products/jeans/slim-fit/front.jpg',
         'https://storage.trend-shop.com/products/jeans/slim-fit/back.jpg'
 )),

-- Sneakers (subcategory_id = 6)
('Urban Sport Sneakers',
 'Lightweight and comfortable everyday sneakers',
 89.99,
 6,
 JSON_ARRAY(
         'https://storage.trend-shop.com/products/sneakers/urban-sport/side.jpg',
         'https://storage.trend-shop.com/products/sneakers/urban-sport/top.jpg',
         'https://storage.trend-shop.com/products/sneakers/urban-sport/sole.jpg'
 ));