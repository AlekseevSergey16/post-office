CREATE TABLE IF NOT EXISTS publisher
(
    id             BIGSERIAL PRIMARY KEY,
    publisher_name TEXT NOT NULL,
    phone          TEXT NOT NULL UNIQUE,
    email          TEXT NOT NULL UNIQUE,
    information    TEXT
);

CREATE TABLE IF NOT EXISTS publication
(
    id               BIGSERIAL PRIMARY KEY,
    publication_name TEXT          NOT NULL,
    about            TEXT,
    cost             DECIMAL(8, 2) NOT NULL,
    pages            INT           NOT NULL,
    weight           INT           NOT NULL,
    duration         INT           NOT NULL,
    publisher_id     BIGINT        NOT NULL,
    FOREIGN KEY (publisher_id) REFERENCES publisher (id)
);

CREATE TABLE IF NOT EXISTS postman
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  TEXT NOT NULL,
    last_name   TEXT NOT NULL,
    middle_name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS address
(
    id               BIGSERIAL PRIMARY KEY,
    street_name      TEXT,
    house_number     INT,
    apartment_number INT,
    postman_id       BIGINT NOT NULL,
    FOREIGN KEY (postman_id) REFERENCES postman (id)
);

CREATE TABLE IF NOT EXISTS subscriber
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  TEXT   NOT NULL,
    last_name   TEXT   NOT NULL,
    middle_name TEXT,
    address_id  BIGINT NOT NULL,
    phone       TEXT   NOT NULL,
    email       TEXT   NOT NULL,
    FOREIGN KEY (address_id) REFERENCES address (id)
);

CREATE TABLE IF NOT EXISTS subscription
(
    id               BIGSERIAL PRIMARY KEY,
    subscriber_id    BIGINT        NOT NULL,
    publication_id   BIGINT        NOT NULL,
    start_date       DATE          NOT NULL,
    end_date         DATE          NOT NULL,
    cost_total       DECIMAL(8, 2) NOT NULL,
    number_of_months INT           NOT NULL,
    FOREIGN KEY (subscriber_id) REFERENCES subscriber (id),
    FOREIGN KEY (publication_id) REFERENCES publication (id)
);



