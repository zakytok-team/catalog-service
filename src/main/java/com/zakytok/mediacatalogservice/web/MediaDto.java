package com.zakytok.mediacatalogservice.web;

import com.zakytok.mediacatalogservice.domain.MediaType;
import com.zakytok.mediacatalogservice.domain.MediaValid;

import java.util.Set;
import java.util.UUID;

public record MediaDto(
        UUID id,
        String title,
        UUID authorId,
        UUID labelId,
        int year,
        MediaType type,
        MediaValid valid,
        Set<String> genres,
        Set<TrackDto> tracks
) {
    public static MediaDto of(String title, UUID authorId, UUID labelId, int year, MediaType type, Set<String> genres, Set<TrackDto> tracks) {
        return new MediaDto(null, title, authorId, labelId, year, type, null, genres, tracks);
    }

    public static MediaDto of(String title, UUID authorId, UUID labelId, int year, MediaType type, MediaValid valid, Set<String> genres, Set<TrackDto> tracks) {
        return new MediaDto(null, title, authorId, labelId, year, type, valid, genres, tracks);
    }
}
