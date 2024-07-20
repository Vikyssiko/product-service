INSERT INTO product (name, brand, model, amount, weight, rating, category, description, color, price)
VALUES
    ('Наушники Acme', 'Acme', 'Model X1', 100, '0.3 kg', 4.5, 'Электроника', 'Прекрасные наушники Acme, активное шумоподавление', 'Черный', 199.99),
    ('Смартфон Galaxy', 'Samsung', 'Galaxy S20', 50, '0.2 kg', 4.8, 'Электроника', 'Мощный смартфон Samsung Galaxy S20', 'Синий', 899.99),
    ('Ноутбук Swift', 'Acer', 'Swift 5', 20, '1.0 kg', 4.2, 'Электроника', 'Легкий и компактный ноутбук Acer Swift 5', 'Серебристый', 1299.99),
    ('Кофемашина Delonghi', 'Delonghi', 'ECAM 22.110.B', 30, '8.0 kg', 4.6, 'Бытовая техника', 'Кофемашина Delonghi с автоматическим капучинатором', 'Черный', 499.99);
;

INSERT INTO product_feature (product_id, feature)
VALUES
    (1, 'Активное шумоподавление'),
    (1, 'Складной дизайн'),
    (1, 'Встроенный микрофон'),
    (2, 'Два слота для SIM'),
    (4, 'Автоматический капучинатор'),
    (4, 'Программируемые рецепты'),
    (4, 'Компактный дизайн');