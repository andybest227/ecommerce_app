-- Insert two real categories
insert into public.category (id, name, description) values
(nextval('category_seq'), 'Electronics', 'Devices and gadgets'),
(nextval('category_seq'), 'Home Appliances', 'Appliances for home use');

-- Insert five real products with appropriate categories
insert into public.product (id, name, description, available_quantity, price, category_id) values
(nextval('product_seq'), 'Smartphone', 'Latest model with advanced features', 50, 699.99, (select id from category where name = 'Electronics')),
(nextval('product_seq'), 'Laptop', 'High-performance laptop for work and play', 30, 1299.99, (select id from category where name = 'Electronics')),
(nextval('product_seq'), 'Microwave Oven', 'Compact microwave for quick meals', 20, 199.99, (select id from category where name = 'Home Appliances')),
(nextval('product_seq'), 'Refrigerator', 'Energy-efficient fridge with large capacity', 15, 899.99, (select id from category where name = 'Home Appliances')),
(nextval('product_seq'), 'Bluetooth Speaker', 'Portable speaker with excellent sound quality', 40, 149.99, (select id from category where name = 'Electronics'));
