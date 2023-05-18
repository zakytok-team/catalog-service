package com.zakytok.catalogservice.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Instant;

public record Item(
        @Id
        Long id,
        String title,
        String author,
        int year,
        ItemType type,
        ItemValid valid,
        @CreatedDate Instant createdDate,
        @LastModifiedDate Instant lastModifiedDate,
        @Version Integer version) {
    public static Item of(String title, String author, int year, ItemType type, ItemValid valid) {
        return new Item(null, title, author, year, type, valid, null, null, 0);
    }
}

