CREATE TABLE genre
(
    id          serial PRIMARY KEY NOT NULL,
    name        VARCHAR(50) NOT NULL,
    parent_id   INT,
    FOREIGN KEY (parent_id) REFERENCES genre (id)
);