package com.zakytok.catalogservice.domain;

import com.zakytok.catalogservice.web.ItemDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public ItemDto create(ItemDto item) {
        if (isUnique(item)) {
            Item toSave = Item.of(item.getTitle(), item.getAuthor(), item.getYear(), item.getType(), ItemValid.VALID);
            Item saved = itemRepository.save(toSave);
            return itemMapper.toItemDto(saved);
        } else {
            throw new ItemNotUniqueException("Item " + item + " is not unique!");
        }
    }

    private boolean isUnique(ItemDto item) {
        return !itemRepository.existsByTitleAndAuthorAndYearAndType(item.getTitle(), item.getAuthor(), item.getYear(), item.getType());
    }

    @Override
    public List<ItemDto> getAllItems() {
        Iterable<Item> items = itemRepository.findAll();
        return itemMapper.allToDtos(items);
    }
}
