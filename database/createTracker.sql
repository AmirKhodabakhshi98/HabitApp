CREATE TABLE habit_tracker (
                               id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                               habits_id BIGINT NOT NULL REFERENCES habits(id),
                               created_date TIMESTAMP NOT NULL,
                               completed_date TIMESTAMP NOT NULL,
                               value DOUBLE PRECISION,
                               note VARCHAR(255)
);