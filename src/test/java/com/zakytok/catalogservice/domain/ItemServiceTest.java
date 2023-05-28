package com.zakytok.catalogservice.domain;

import com.zakytok.catalogservice.web.ItemDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;

    @Mock
    ItemMapper itemMapper;

    @InjectMocks
    ItemServiceImpl itemService;

    @Test
    void createItem() {
        ItemDto toCreate = new ItemDto("title1", "author1", 1989, ItemType.VINYL);
        ItemDto createdDto = new ItemDto(UUID.randomUUID(),"title1", "author1", 1989, ItemType.VINYL, ItemValid.VALID);
        when(itemRepository.existsByTitleAndAuthorAndYearAndType(toCreate.getTitle(), toCreate.getAuthor(), toCreate.getYear(), toCreate.getType()))
                .thenReturn(false);
        when(itemMapper.toItemDto(any())).thenReturn(createdDto);

        ItemDto result = itemService.create(toCreate);

        assertThat(result).matches(item -> item.getTitle().equals(toCreate.getTitle())
                && item.getAuthor().equals(toCreate.getAuthor())
                && item.getYear() == toCreate.getYear()
                && item.getValid().equals(ItemValid.VALID));
    }

    @Test
    void createItemNotUnique() {
        ItemDto toCreate = new ItemDto("title1", "author1", 1989, ItemType.VINYL);
        Item existing = Item.of("title1", "author1", 1989, ItemType.VINYL, ItemValid.VALID);
        when(itemRepository.existsByTitleAndAuthorAndYearAndType(
                toCreate.getTitle(),
                toCreate.getAuthor(),
                toCreate.getYear(),
                toCreate.getType())).thenReturn(true);

        assertThatThrownBy(() -> itemService.create(toCreate))
                .isInstanceOf(ItemNotUniqueException.class)
                .hasMessage("Item " + toCreate + " is not unique!");
    }

    @Test
    void getAllItems() {
        List<Item> items = List.of(Item.of( "title1", "author1", 1989, ItemType.VINYL, ItemValid.VALID));
        List<ItemDto> itemsDto = List.of(new ItemDto("title1", "author1", 1989, ItemType.VINYL));
        when(itemRepository.findAll()).thenReturn(items);
        when(itemMapper.allToDtos(items)).thenReturn(itemsDto);

        List<ItemDto> res = itemService.getAllItems();
        assertThat(res).isEqualTo(itemsDto);
    }

    @Test
    void uploadImageToAWS() {
    }
}
