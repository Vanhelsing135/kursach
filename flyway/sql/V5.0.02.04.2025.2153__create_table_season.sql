CREATE TABLE Season(
    id             BIGINT PRIMARY KEY,
    competition_id BIGINT NOT NULL,
    start_date     DATE   NOT NULL,
    end_date       DATE   NOT NULL,
    winner_id      BIGINT,
    FOREIGN KEY (competition_id) REFERENCES Competition(id) ON DELETE CASCADE,
    FOREIGN KEY (winner_id) REFERENCES Team(id) ON DELETE SET NULL
);
