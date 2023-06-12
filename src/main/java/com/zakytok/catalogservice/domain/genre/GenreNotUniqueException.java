package com.zakytok.catalogservice.domain.genre;

public class GenreNotUniqueException extends RuntimeException {
    public GenreNotUniqueException(String message) {
        super(message);
    }
}
