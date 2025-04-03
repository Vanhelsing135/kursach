CREATE TABLE Scorer(
    id        BIGINT PRIMARY KEY,
    player_id BIGINT NOT NULL,
    team_id   BIGINT NOT NULL,
    goals     INT    NOT NULL,
    assists   INT    NOT NULL,
    penalties INT    NOT NULL,
    FOREIGN KEY (player_id) REFERENCES Player (id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES Team (id) ON DELETE CASCADE
);