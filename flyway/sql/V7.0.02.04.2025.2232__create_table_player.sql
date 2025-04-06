CREATE TABLE Player(
    id            BIGINT PRIMARY KEY,
    team_id       BIGINT,
    name          VARCHAR(255) NOT NULL,
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    date_of_birth DATE,
    nationality   VARCHAR(50),
    position      VARCHAR(50),
    shirt_number  INT,
    last_updated  TIMESTAMP,
    FOREIGN KEY (team_id) REFERENCES Team(id)
);
