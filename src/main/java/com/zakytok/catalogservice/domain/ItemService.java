package com.zakytok.catalogservice.domain;

import com.zakytok.catalogservice.web.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(ItemDto item);

    List<ItemDto> getAllItems();
}
