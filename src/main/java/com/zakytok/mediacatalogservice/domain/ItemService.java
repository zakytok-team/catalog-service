package com.zakytok.mediacatalogservice.domain;

import com.zakytok.mediacatalogservice.web.ItemDto;
import com.zakytok.mediacatalogservice.web.ItemValidDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ItemService {
    ItemDto get(UUID id);

    ItemValidDto isValid(UUID id);

    ItemDto create(ItemDto item);

    void delete(UUID id);

    List<ItemDto> getAllItems();

    ItemDto updateItemGenres(UUID id, Set<String> genres);
}
