package com.zakytok.mediacatalogservice.domain;

public class GenreNotUniqueException extends RuntimeException {
    public GenreNotUniqueException(String message) {
        super(message);
    }
}
