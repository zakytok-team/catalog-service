ALTER TABLE genre
    ADD COLUMN created_date timestamp,
    ADD COLUMN last_modified_date timestamp,
    ADD COLUMN version integer