package com.zakytok.mediacatalogservice.domain;

import com.zakytok.mediacatalogservice.web.ItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public abstract class ItemMapper {

    @Named("mapGenres")
    public Set<String> mapGenres(Set<Genre> genres) {
        return genres.stream().map(Genre::getName).collect(Collectors.toSet());
    }

    @Mapping(source = "genres", target = "genres", qualifiedByName = "mapGenres")
    public abstract ItemDto toItemDto(Item item);

    public abstract List<ItemDto> allToDtos(Iterable<Item> items);
}
