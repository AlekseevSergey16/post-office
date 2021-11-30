CREATE TABLE IF NOT EXISTS publisher
(
    id             BIGSERIAL PRIMARY KEY,
    publisher_name VARCHAR(255) NOT NULL,
    phone          VARCHAR(255) NOT NULL UNIQUE,
    email          VARCHAR(255) NOT NULL UNIQUE,
    information    VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS publication
(
    id               BIGSERIAL PRIMARY KEY,
    publication_name VARCHAR(255)  NOT NULL,
    about            VARCHAR(255),
    cost             DECIMAL(8, 2) NOT NULL,
    pages            INT           NOT NULL,
    weight           INT           NOT NULL,
    periodicity      INT           NOT NULL,
    publisher_id     BIGINT        NOT NULL,
    FOREIGN KEY (publisher_id) REFERENCES publisher (id)
);

CREATE TABLE IF NOT EXISTS postman
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS address
(
    id           BIGSERIAL PRIMARY KEY,
    street_name  VARCHAR(255),
    house_number INT,
    postman_id   BIGINT NOT NULL,
    FOREIGN KEY (postman_id) REFERENCES postman (id)
);

CREATE TABLE IF NOT EXISTS subscriber
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(255)   NOT NULL,
    last_name   VARCHAR(255)   NOT NULL,
    middle_name VARCHAR(255),
    address_id  BIGINT NOT NULL,
    phone       VARCHAR(255)   NOT NULL,
    email       VARCHAR(255)   NOT NULL,
    FOREIGN KEY (address_id) REFERENCES address (id)
);

CREATE TABLE IF NOT EXISTS subscription
(
    id               BIGSERIAL PRIMARY KEY,
    subscriber_id    BIGINT        NOT NULL,
    publication_id   BIGINT        NOT NULL,
    number_of_months INT           NOT NULL,
    start_date       DATE          NOT NULL,
    end_date         DATE          NOT NULL,
    cost_total       DECIMAL(8, 2) NOT NULL,
    FOREIGN KEY (subscriber_id) REFERENCES subscriber (id),
    FOREIGN KEY (publication_id) REFERENCES publication (id)
);

CREATE INDEX IF NOT EXISTS publication_name_index ON publication (lower(publication_name));
CREATE INDEX IF NOT EXISTS publisher_name_index ON publisher (lower(publisher_name));





