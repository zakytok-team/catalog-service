package com.zakytok.catalogservice.domain;

import com.zakytok.catalogservice.web.ItemDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ItemService {
    ItemDto create(ItemDto item);

    List<ItemDto> getAllItems();

    ItemDto updateItemGenres(UUID id, Set<String> genres);
}
