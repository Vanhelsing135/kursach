CREATE TABLE user_favorite_teams (
                                     user_id BIGINT NOT NULL,
                                     team_id INT NOT NULL,
                                     PRIMARY KEY (user_id, team_id),
                                     CONSTRAINT fk_user_teams FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE user_favorite_competitions (
                                            user_id BIGINT NOT NULL,
                                            competition_id INT NOT NULL,
                                            PRIMARY KEY (user_id, competition_id),
                                            CONSTRAINT fk_user_competitions FOREIGN KEY (user_id) REFERENCES user(id)
);