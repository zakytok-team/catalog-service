CREATE TABLE item_genre
(
    item_id  UUID,
    genre_id INT,
    FOREIGN KEY (genre_id) REFERENCES genre (id),
    FOREIGN KEY (item_id) REFERENCES item (id)
);