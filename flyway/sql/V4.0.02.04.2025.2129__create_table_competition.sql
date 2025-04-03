CREATE TABLE Competition(
    id                BIGINT PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    code              VARCHAR(10)  NOT NULL UNIQUE,
    type              VARCHAR(50)  NOT NULL,
    emblem_url        VARCHAR(500),
    country           varchar(50)  NOT NULL,
    plan              VARCHAR(50),
    current_season_id BIGINT,
    last_updated      TIMESTAMP
);
