DELETE FROM product_features;

DELETE FROM products;

ALTER TABLE products ALTER COLUMN id RESTART WITH 1;
