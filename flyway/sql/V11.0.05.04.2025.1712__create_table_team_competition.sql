CREATE TABLE team_competition
(
    team_id        BIGINT,
    competition_id BIGINT,
    PRIMARY KEY (team_id, competition_id),
    FOREIGN KEY (team_id) REFERENCES Team (id),
    FOREIGN KEY (competition_id) REFERENCES Competition (id)
);