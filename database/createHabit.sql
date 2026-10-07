CREATE TABLE habits (
                        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        name VARCHAR(100) NOT null,

                        min_duration_minutes INTEGER,
                        max_duration_minutes INTEGER,
                        normal_duration_minutes INTEGER,

                --        completion_datetime TIMESTAMP, moved to habitsmanager part

                        category VARCHAR(100),
                        location VARCHAR(100),
                        equipment VARCHAR(100),

                        recurrence_type VARCHAR(50),
                        recurrence_value INTEGER
);