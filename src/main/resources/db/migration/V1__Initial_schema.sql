CREATE TABLE item
(
    id                 UUID      PRIMARY KEY NOT NULL,
    title              varchar(255)          NOT NULL,
    author             varchar(255)          NOT NULL,
    year               integer               NOT NULL,
    type               varchar(20)           NOT NULL,
    valid              varchar(20)           NOT NULL,
    created_date       timestamp             NOT NULL,
    last_modified_date timestamp             NOT NULL,
    version            integer               NOT NULL
);