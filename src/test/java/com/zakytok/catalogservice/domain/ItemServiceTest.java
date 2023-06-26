package com.zakytok.catalogservice.domain;

import com.zakytok.mediacatalogservice.domain.*;
import com.zakytok.mediacatalogservice.web.ItemDto;
import com.zakytok.mediacatalogservice.web.ItemValidDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;

    @Mock
    GenreRepository genreRepository;

    @Mock
    ItemMapper itemMapper;

    @InjectMocks
    ItemServiceImpl itemService;

    @Test
    void createItem() {
        ItemDto toCreate = ItemDto.of("title1", "author1", 1989, ItemType.VINYL, Set.of("techno"));
        when(itemRepository.existsByTitleAndAuthorAndYearAndType(toCreate.title(), toCreate.author(), toCreate.year(), toCreate.type()))
                .thenReturn(false);
        when(genreRepository.findByName("techno")).thenReturn(Optional.of(Genre.of("techno")));

        itemService.create(toCreate);

        verify(itemRepository).save(any());
    }

    @Test
    void createItemNotUnique() {
        ItemDto toCreate = ItemDto.of("title1", "author1", 1989, ItemType.VINYL, Set.of("techno"));
        when(itemRepository.existsByTitleAndAuthorAndYearAndType(
                toCreate.title(),
                toCreate.author(),
                toCreate.year(),
                toCreate.type())).thenReturn(true);

        assertThatThrownBy(() -> itemService.create(toCreate))
                .isInstanceOf(ItemNotUniqueException.class)
                .hasMessage("Item " + toCreate + " is not unique!");
    }

    @Test
    void itemNotValid() {
        UUID itemId = UUID.randomUUID();
        Item notValidItem = new Item();
        notValidItem.setValid(ItemValid.INVALID);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(notValidItem));

        ItemValidDto itemValid = itemService.isValid(itemId);

        assert !itemValid.isValid();
    }

    @Test
    void itemValid() {
        UUID itemId = UUID.randomUUID();
        Item notValidItem = new Item();
        notValidItem.setValid(ItemValid.VALID);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(notValidItem));

        ItemValidDto itemValid = itemService.isValid(itemId);

        assert itemValid.isValid();
    }

    @Test
    void uploadImageToAWS() {
    }
}
