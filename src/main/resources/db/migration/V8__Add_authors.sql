CREATE TABLE author
(
    id                 UUID PRIMARY KEY                                            NOT NULL,
    name               varchar(255)                                                NOT NULL,
    realName           varchar(255),
    info               varchar(255),
    imageUrl           varchar(300),
    type               varchar(20),
    created_date       timestamp    DEFAULT CURRENT_DATE                           NOT NULL,
    last_modified_date timestamp    DEFAULT CURRENT_DATE                           NOT NULL,
    version            integer      DEFAULT 0                                      NOT NULL,
    created_by         varchar(255) DEFAULT 'c06d4b0c-bd6c-488a-a1ba-454bc97caf87' NOT NULL,
    last_modified_by   varchar(255) DEFAULT 'c06d4b0c-bd6c-488a-a1ba-454bc97caf87' NOT NULL
);

INSERT INTO author(id, name)
SELECT gen_random_uuid(), author
FROM (SELECT DISTINCT author FROM media) AS distinct_authors;

ALTER TABLE author
    ALTER COLUMN created_by DROP DEFAULT,
    ALTER COLUMN last_modified_by DROP DEFAULT,
    ALTER COLUMN created_date DROP DEFAULT,
    ALTER COLUMN last_modified_date DROP DEFAULT,
    ALTER COLUMN version DROP DEFAULT;

ALTER TABLE media
    ADD COLUMN author_id UUID,
    ADD CONSTRAINT fk_media_author FOREIGN KEY (author_id)
        REFERENCES author (id);

UPDATE media m
SET author_id = a.id
FROM author a
WHERE m.author = a.name;

ALTER TABLE media
    DROP COLUMN author;


