package com.zakytok.catalogservice.domain;

public class ItemNotUniqueException extends RuntimeException {
    public ItemNotUniqueException(String message) {
        super(message);
    }
}
