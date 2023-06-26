package com.zakytok.mediacatalogservice.domain;

public class MediaNotUniqueException extends RuntimeException {
    public MediaNotUniqueException(String message) {
        super(message);
    }
}
