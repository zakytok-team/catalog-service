package com.zakytok.catalogservice.domain.item;

import com.zakytok.catalogservice.web.ItemDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemDto toItemDto(Item item);

    List<ItemDto> allToDtos(Iterable<Item> items);
}
