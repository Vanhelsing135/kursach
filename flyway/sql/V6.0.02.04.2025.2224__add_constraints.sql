ALTER TABLE Competition
    ADD CONSTRAINT fk_competition_season
        FOREIGN KEY (current_season_id) REFERENCES Season(id);