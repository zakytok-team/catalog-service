CREATE TABLE label
(
    id                 UUID PRIMARY KEY NOT NULL,
    name               varchar(255)     NOT NULL,
    year_created       varchar(255)     NOT NULL,
    created_date       timestamp        NOT NULL,
    last_modified_date timestamp        NOT NULL,
    version            integer          NOT NULL,
    created_by         varchar(255)     NOT NULL,
    last_modified_by   varchar(255)     NOT NULL

);

ALTER TABLE media
    ADD COLUMN label_id UUID,
    ADD CONSTRAINT fk_media_label FOREIGN KEY (label_id) REFERENCES label (id);