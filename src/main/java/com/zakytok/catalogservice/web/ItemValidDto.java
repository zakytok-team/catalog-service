package com.zakytok.catalogservice.web;

import java.util.UUID;

public record ItemValidDto(
        UUID id,
        boolean isValid
) {
    public static ItemValidDto of(UUID id, boolean valid) {
        return new ItemValidDto(id, valid);
    }
}
