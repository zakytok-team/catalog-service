package com.zakytok.mediacatalogservice.web;

import java.util.UUID;

public record MediaValidDto(
        UUID id,
        boolean isValid
) {
    public static MediaValidDto of(UUID id, boolean valid) {
        return new MediaValidDto(id, valid);
    }
}
