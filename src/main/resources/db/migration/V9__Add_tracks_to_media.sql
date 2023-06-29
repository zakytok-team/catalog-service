CREATE TABLE track
(
    id                 SERIAL PRIMARY KEY NOT NULL,
    media_id           UUID               NOT NULL,
    name               varchar(255)       NOT NULL,
    position           varchar(50)        NOT NULL,
    seconds_duration   INT                NOT NULL,
    created_date       timestamp          NOT NULL,
    last_modified_date timestamp          NOT NULL,
    version            integer            NOT NULL,
    created_by         varchar(255)       NOT NULL,
    last_modified_by   varchar(255)       NOT NULL,
    FOREIGN KEY (media_id) REFERENCES media (id)
);

CREATE TABLE track_author
(
    track_id  INT,
    author_id UUID,
    FOREIGN KEY (track_id) REFERENCES track (id),
    FOREIGN KEY (author_id) REFERENCES author (id)
)