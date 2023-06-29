package com.zakytok.mediacatalogservice.web;

import com.zakytok.mediacatalogservice.domain.AuthorType;

import java.util.Set;
import java.util.UUID;

public record AuthorDto(
        UUID id,
        String name,
        String realName,
        String info,
        String imageUrl,
        AuthorType type,
        Set<MediaDto> mediaSet
) {
    public static AuthorDto of(String name, String realName, String info, String imageUrl, AuthorType type, Set<MediaDto> mediaSet) {
        return new AuthorDto(null, name, realName, info, imageUrl, type, mediaSet);
    }
}
