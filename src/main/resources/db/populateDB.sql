DELETE
FROm RATED_RESTAURANTS;
DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM restaurants;
DELETE
FROM dishes;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('ADMIN', 100001);


INSERT INTO restaurants (name, rating)
VALUES ('MamaS House', 2),
       ('My own Company', 3),
       ('HoundS Pit', 10);

INSERT INTO RATED_RESTAURANTS (user_id, restaurant_id, RESTAURANT_NAME)
VALUES (100000, 100002, 'MamaS house'),
       (100001, 100003, 'My own Company'),
       (100000, 100004, 'HoundS Pit');

-- INSERT INTO menus (restaurant_id)
-- VALUES (100002),
-- --        menu_id: 100005
--        (100003),
-- --        menu_id: 100006
--        (100004),
-- --        menu_id: 100007
--        (100002),
-- --        menu_id: 100008!!
--        (100003),
-- --        menu_id: 100009!!
--        (100004),
-- --        menu_id: 100010!!
--        (100002);
-- --        menu_id: 100011!!

INSERT INTO dishes (restaurant_id, name, price)
VALUES (100002, 'soup', 10.99),
       (100002, 'roast beef', 13.02),
       (100002, 'fried eggs', 11.00),
       (100002, 'muffin', 6.33),
       (100003, 'pancakes', 5.10),
       (100003, 'ice cream', 12.10),
       (100003, 'vanilla cake', 17.00),
       (100004, 'cheeseburger', 7.55),
       (100004, 'pizza', 15.00),
       (100004, 'lasagna', 14.20);



--        INSERT INTO dishes (menu_id, name, price)
-- VALUES (100005, 'soup', 10.99),
--        (100010, 'soup', 10.99),
--        (100006, 'roastbeef', 13.02),
--        (100008, 'roastbeef', 13.02),
--        (100011, 'roastbeef', 13.02),
--        (100007, 'pancakes', 5.10),
--        (100009, 'pancakes', 5.10),
--        (100007, 'fried eggs', 11.00),
--        (100009, 'fried eggs', 11.00),
--        (100005, 'muffin', 6.33),
--        (100010, 'muffin', 6.33),
--        (100006, 'cheesburger', 7.55),
--        (100008, 'cheesburger', 7.55),
--        (100011, 'cheesburger', 7.55),
--        (100007, 'pizza', 15.00),
--        (100009, 'pizza', 15.00),
--        (100007, 'lasagna', 14.20),
--        (100009, 'lasagna', 14.20),
--        (100005, 'icecream', 12.10),
--        (100010, 'icecream', 12.10),
--        (100006, 'vanilla cake', 17.00),
--        (100008, 'vanilla cake', 17.00),
--        (100011, 'vanilla cake', 17.00);