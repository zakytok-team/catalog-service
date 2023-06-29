package com.zakytok.mediacatalogservice.domain;

import com.zakytok.mediacatalogservice.web.AuthorDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public abstract class AuthorMapper {

    public abstract AuthorDto toDto(Author author);

    public abstract List<AuthorDto> allToDto(Iterable<Author> authors);

    Set<String> genreToString(Set<Genre> genres) {
        return genres.stream().map(Genre::getName).collect(Collectors.toSet());
    }
}
