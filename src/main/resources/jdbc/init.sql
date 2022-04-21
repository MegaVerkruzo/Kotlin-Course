CREATE TABLE orders
(
    order_id        serial PRIMARY KEY,
    cake_name       VARCHAR(50) UNIQUE NOT NULL,
    cake_cost       FLOAT              NOT NULL,
    cake_count      INT                NOT NULL,
    order_completed BIT                NOT NULL
);

CREATE TABLE cakes
(
    cake_id    serial PRIMARY KEY,
    cake_name  VARCHAR(50) UNIQUE NOT NULL,
    cake_cost  FLOAT              NOT NULL,
    cake_count INT                NOT NULL
);