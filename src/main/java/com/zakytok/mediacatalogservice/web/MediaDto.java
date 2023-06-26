package com.zakytok.mediacatalogservice.web;

import com.zakytok.mediacatalogservice.domain.MediaType;
import com.zakytok.mediacatalogservice.domain.MediaValid;

import java.util.Set;
import java.util.UUID;

public record MediaDto(
        UUID id,
        String title,
        String author,
        int year,
        MediaType type,
        MediaValid valid,
        Set<String> genres
) {
    public static MediaDto of(String title, String author, int year, MediaType type, Set<String> genres) {
        return new MediaDto(null, title, author, year, type, null, genres);
    }

    public static MediaDto of(String title, String author, int year, MediaType type, MediaValid valid, Set<String> genres) {
        return new MediaDto(null, title, author, year, type, valid, genres);
    }
}
