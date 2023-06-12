package com.zakytok.catalogservice.domain.genre;

import com.zakytok.catalogservice.web.GenreDto;

import java.util.List;

public interface GenreService {

    GenreDto create(GenreDto toCreate);

    List<GenreDto> getAll();
}
