CREATE TABLE Team(
    id           BIGINT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    short_name   VARCHAR(50),
    tla          VARCHAR(10) UNIQUE,
    address      VARCHAR(255),
    founded      INT,
    club_colors  VARCHAR(100),
    venue        VARCHAR(255)
);
