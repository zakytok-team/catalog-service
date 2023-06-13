package com.zakytok.catalogservice.domain;

public class GenreNotUniqueException extends RuntimeException {
    public GenreNotUniqueException(String message) {
        super(message);
    }
}
