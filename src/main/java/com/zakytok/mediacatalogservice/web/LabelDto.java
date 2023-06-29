package com.zakytok.mediacatalogservice.web;

import java.util.UUID;

public record LabelDto(
        UUID id,
        String name,
        int yearCreated
) {

    public static LabelDto of(String name, int yearCreated) {
        return new LabelDto(null, name, yearCreated);
    }

    public static LabelDto of(UUID id, String name, int yearCreated) {
        return new LabelDto(id, name, yearCreated);
    }
}
