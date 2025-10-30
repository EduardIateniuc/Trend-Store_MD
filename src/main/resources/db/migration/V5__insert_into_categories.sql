-- First, we populate the categories table with main product categories
-- Each category has a descriptive name, gender specification, and sample images
INSERT INTO categories (category_name, gender, image_url) VALUES
                                                           ('Clothing', 'unisex', JSON_ARRAY(
                                                                   'https://storage.trend-shop.com/categories/clothing/main.jpg',
                                                                   'https://storage.trend-shop.com/categories/clothing/banner.jpg'
                                                                                  )),
                                                           ('Shoes', 'unisex', JSON_ARRAY(
                                                                   'https://storage.trend-shop.com/categories/shoes/main.jpg',
                                                                   'https://storage.trend-shop.com/categories/shoes/banner.jpg'
                                                                               )),
                                                           ('Accessories', 'unisex', JSON_ARRAY(
                                                                   'https://storage.trend-shop.com/categories/accessories/main.jpg',
                                                                   'https://storage.trend-shop.com/categories/accessories/banner.jpg'
                                                                                     ));