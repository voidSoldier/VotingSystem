DROP TABLE rated_restaurants IF EXISTS;
DROP TABLE user_roles IF EXISTS;
DROP TABLE dishes IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP TABLE users IF EXISTS;


DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
    id          INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name        VARCHAR(255)            NOT NULL,
    email       VARCHAR(255)            NOT NULL,
    password    VARCHAR(255)            NOT NULL,
    registered  TIMESTAMP DEFAULT now() NOT NULL,
    enabled     BOOLEAN   DEFAULT TRUE  NOT NULL,
    has_voted   BOOLEAN   DEFAULT FALSE,
    voting_time DATETIME,
    restaurant_id INTEGER
--     FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id)

);

CREATE UNIQUE INDEX users_unique_email_idx
    ON USERS (email);

CREATE TABLE restaurants
(
    id      INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name    VARCHAR(255)      NOT NULL,
    rating  INTEGER DEFAULT 0 NOT NULL
--     user_id INTEGER,
--     FOREIGN KEY (user_id) REFERENCES USERS (id)
);


CREATE TABLE dishes
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    restaurant_id INTEGER      NOT NULL,
    name          VARCHAR(255) NOT NULL,
    price         DOUBLE       NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id) ON DELETE CASCADE
);



CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);


CREATE TABLE rated_restaurants
(
    user_id         INTEGER      NOT NULL,
    restaurant_id   INTEGER      NOT NULL,
    restaurant_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (id),
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id)
--     FOREIGN KEY (restaurant_name ) REFERENCES RESTAURANTS (name)
)

-- CREATE UNIQUE INDEX meals_unique_user_datetime_idx
--     ON meals (user_id, date_time);


-- CREATE TABLE menus
-- (
--     id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
--     restaurant_id INTEGER NOT NULL,
--     FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id) ON DELETE CASCADE
-- );

