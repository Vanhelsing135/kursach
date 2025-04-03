CREATE TABLE Player(
    id            BIGINT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    date_of_birth DATE,
    nationality   VARCHAR(50),
    position      VARCHAR(50),
    shirt_number  INT,
    last_updated  TIMESTAMP
);
