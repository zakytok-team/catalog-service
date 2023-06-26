package com.zakytok.mediacatalogservice.domain;

import com.zakytok.mediacatalogservice.web.GenreDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Mapper(componentModel = "spring")
public interface GenreMapper {

    @Mapping(target="parentId", source="parent.id")
    GenreDto toDto(Genre genre);

    Set<GenreDto> allToDtos(Iterable<Genre> genres);
}
