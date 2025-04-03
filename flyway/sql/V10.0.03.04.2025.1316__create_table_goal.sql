CREATE TABLE Goal (
    id BIGINT PRIMARY KEY,
    match_id BIGINT NOT NULL,
    player_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    goal_time INT NOT NULL,
    FOREIGN KEY (match_id) REFERENCES Matches(id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES Player(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES Team(id) ON DELETE CASCADE
);
