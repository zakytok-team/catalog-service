package com.zakytok.catalogservice.domain;

import com.zakytok.catalogservice.web.ItemDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final GenreRepository genreRepository;

    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper, GenreRepository genreRepository) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.genreRepository = genreRepository;
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
