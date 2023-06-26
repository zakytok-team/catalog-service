ALTER TABLE item RENAME TO media;
ALTER TABLE item_genre RENAME TO media_genre;
ALTER TABLE media_genre RENAME COLUMN item_id TO media_id