DELETE FROM user_votes;
DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');


INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('ADMIN', 100001);


INSERT INTO restaurants (name, rating)
VALUES ('MamaS House', 2),
       ('My own Company', 3),
       ('HoundS Pit', 10);


INSERT INTO user_votes (user_id, vote_date, restaurant_name, restaurant_id, menu)
VALUES (100000, '2020-01-30 10:00:00', 'MamaS House', 100002, 'soup - 10.99'+ CHAR(13) + 'roast beef - 13.02'+ CHAR(13) + 'fried eggs - 11.00'+ CHAR(13) + 'muffin - 6.33'+ CHAR(13)),
       (100001, '2020-01-30 20:00:00', 'My own Company', 100003, 'pancakes - 5.10'+ CHAR(13) + 'nice cream - 12.10'+ CHAR(13) + 'vanilla cake - 17.00'+ CHAR(13)),
       (100000, '2020-01-31 0:00:00', 'HoundS Pit', 100004, 'cheeseburger - 7.55'+ CHAR(13) + 'pizza - 15.00' + CHAR(13) + 'lasagna - 14.20'+ CHAR(13));


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

