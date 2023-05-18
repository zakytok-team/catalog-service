package com.zakytok.catalogservice.domain;

import com.zakytok.catalogservice.web.ItemDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

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
        ItemDto createdDto = new ItemDto("title1", "author1", 1989, ItemType.VINYL, ItemValid.VALID);
        when(itemRepository.findBy(toCreate.getTitle(), toCreate.getAuthor(), toCreate.getYear(), toCreate.getType()))
                .thenReturn(Optional.empty());
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
        when(itemRepository.findBy(
                toCreate.getTitle(),
                toCreate.getAuthor(),
                toCreate.getYear(),
                toCreate.getType())).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> itemService.create(toCreate))
                .isInstanceOf(ItemNotUniqueException.class)
                .hasMessage("Item " + toCreate + " is not unique!");
    }

    @Test
    void getAllItems() {
        List<ItemDto> items = List.of(new ItemDto("title1", "author1", 1989, ItemType.VINYL));
        when(itemRepository.getAllDtos()).thenReturn(items);

        List<ItemDto> res = itemService.getAllItems();
        assertThat(res).isEqualTo(items);
    }

    @Test
    void uploadImageToAWS() {
    }
}
