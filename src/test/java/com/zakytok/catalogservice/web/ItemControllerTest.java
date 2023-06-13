package com.zakytok.catalogservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zakytok.catalogservice.domain.ItemNotUniqueException;
import com.zakytok.catalogservice.domain.ItemService;
import com.zakytok.catalogservice.domain.ItemType;
import com.zakytok.catalogservice.domain.ItemValid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ItemService itemService;

    @Test
    void getAllItemsTestSuccessful() throws Exception {
        List<ItemDto> items = List.of(ItemDto.of("title1", "author1", 1989, ItemType.VINYL, Set.of("techno")));
        when(itemService.getAllItems()).thenReturn(items);

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(items)));
    }

    @Test
    void createItem() throws Exception {
        /*
        title
        author
        year
        type (VINYL, CD, CASSETTE, OTHER)
        genres many to many
        tracklist one to many
        valid (VALID, ON_REVIEW, INVALID)
        */
        ItemDto toCreate = ItemDto.of("title1", "author1", 1989, ItemType.VINYL, Set.of("techno"));
        ItemDto created = ItemDto.of("title1", "author1", 1989, ItemType.VINYL, ItemValid.VALID, Set.of("techno"));

        when(itemService.create(any())).thenReturn(created);

        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(created)));
    }

    @Test
    void createItemNotUnique() throws Exception {
        ItemDto toCreate = ItemDto.of("title1", "author1", 1989, ItemType.VINYL, Set.of("techno"));
        String exceptionMessage = "Item " + toCreate + " not unique!";
        when(itemService.create(toCreate)).thenThrow(new ItemNotUniqueException(exceptionMessage));

        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(toCreate)))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(exceptionMessage));
    }
}