package com.zakytok.mediacatalogservice.web;

import java.util.Set;
import java.util.UUID;

public record TrackDto(
        Long id,
        String name,
        String position,
        int secondsDuration,
        UUID mediaId,
        Set<UUID> authorIds
) {

    public static TrackDto of(String name, String position, int secondsDuration, Set<UUID> authorIds) {
        return new TrackDto(null, name, position, secondsDuration, null, authorIds);
    }
}
