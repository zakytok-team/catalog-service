package com.zakytok.catalogservice.domain;

import com.zakytok.catalogservice.web.GenreDto;

import java.util.Set;

public interface GenreService {

    GenreDto create(GenreDto toCreate);

    Set<GenreDto> getAll();

    GenreDto update(Long genreId, String newValue);

    void delete(Long genreId);
}
