package com.zakytok.mediacatalogservice.domain;

import com.zakytok.mediacatalogservice.web.MediaDto;
import com.zakytok.mediacatalogservice.web.MediaValidDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface MediaService {
    MediaDto get(UUID id);

    MediaValidDto isValid(UUID id);

    MediaDto create(MediaDto mediaDto);

    void delete(UUID id);

    List<MediaDto> getAll();

    MediaDto updateMediaGenres(UUID id, Set<String> genres);
}
