package com.zakytok.catalogservice.domain.genre;

import com.zakytok.catalogservice.web.GenreDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface GenreMapper {

    @Mapping(target="parentId", source="parent.id")
    GenreDto toDto(Genre genre);

    List<GenreDto> allToDtos(Iterable<Genre> genres);
}
