package com.zakytok.catalogservice.web;

import jakarta.validation.constraints.NotEmpty;

import java.util.Collections;
import java.util.List;

public record GenreDto(
        Long parentId,
        @NotEmpty
        String name,
        List<GenreDto> children
) {
    public static GenreDto of(String name) {
        return new GenreDto(null, name, Collections.emptyList());
    }

    public static GenreDto of(Long parentId, String name) {
        return new GenreDto(parentId, name, Collections.emptyList());
    }
}
