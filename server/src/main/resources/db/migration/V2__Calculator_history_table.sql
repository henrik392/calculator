CREATE TABLE calculator_history
(
    id         SERIAL PRIMARY KEY,
    expression VARCHAR(255)     NOT NULL,
    result     DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    username   VARCHAR(255)     NOT NULL REFERENCES users (username)
);