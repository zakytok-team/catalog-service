package com.zakytok.mediacatalogservice.domain;

public class ItemNotUniqueException extends RuntimeException {
    public ItemNotUniqueException(String message) {
        super(message);
    }
}
