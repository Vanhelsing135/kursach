CREATE TABLE IF NOT EXISTS Question (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          question VARCHAR(500) NOT NULL,
                          answer_id BIGINT NOT NULL,
                          CONSTRAINT fk_question_answer
                              FOREIGN KEY (answer_id)
                                  REFERENCES Answer(id)
                                  ON DELETE CASCADE
);