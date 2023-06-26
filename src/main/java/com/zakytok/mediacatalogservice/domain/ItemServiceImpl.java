package com.zakytok.mediacatalogservice.domain;

import com.zakytok.mediacatalogservice.web.ItemDto;
import com.zakytok.mediacatalogservice.web.ItemValidDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final GenreRepository genreRepository;

    public ItemDto get(UUID id) {
        Item item = findItem(id);
        return itemMapper.toItemDto(item);
    }

    public ItemValidDto isValid(UUID id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            boolean valid = item.get().getValid().equals(ItemValid.VALID);
            return ItemValidDto.of(id, valid);
        } else {
            return ItemValidDto.of(id, false);
        }
    }

    public List<ItemDto> getAllItems() {
        Iterable<Item> items = itemRepository.findAll();
        return itemMapper.allToDtos(items);
    }

    public ItemDto updateItemGenres(UUID id, Set<String> genresNames) {
        Item toUpdate = findItem(id);
        Set<Genre> genres = mapGenres(genresNames);
        toUpdate.setGenres(genres);
        return itemMapper.toItemDto(toUpdate);
    }

    private Item findItem(UUID id) {
        return itemRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Item with id " + id + " not exists!"));
    }

    public ItemDto create(ItemDto itemDto) {
        if (isUnique(itemDto)) {
            Item toSave = build(itemDto);
            Item saved = itemRepository.save(toSave);
            return itemMapper.toItemDto(saved);
        } else {
            throw new ItemNotUniqueException("Item " + itemDto + " is not unique!");
        }
    }

    @Override
    public void delete(UUID id) {
        Item toDelete = findItem(id);
        itemRepository.delete(toDelete);
    }

    private boolean isUnique(ItemDto item) {
        return !itemRepository.existsByTitleAndAuthorAndYearAndType(item.title(), item.author(), item.year(), item.type());
    }

    private Item build(ItemDto itemDto) {
        Set<Genre> genres = mapGenres(itemDto.genres());
        return Item.of(itemDto.title(), itemDto.author(), itemDto.year(), itemDto.type(), ItemValid.VALID, genres);
    }

    private Set<Genre> mapGenres(Set<String> names) {
        return names.stream().map(name ->
                genreRepository.findByName(name)
                        .orElseThrow(() -> new NoSuchElementException("Genre with name " + name + " does not exist."))
        ).collect(toSet());
    }
}
