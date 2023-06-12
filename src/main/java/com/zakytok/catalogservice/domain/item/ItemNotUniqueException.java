package com.zakytok.catalogservice.domain.item;

public class ItemNotUniqueException extends RuntimeException {
    public ItemNotUniqueException(String message) {
        super(message);
    }
}
