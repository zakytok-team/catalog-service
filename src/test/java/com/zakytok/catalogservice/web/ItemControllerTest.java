package com.zakytok.catalogservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zakytok.mediacatalogservice.config.SecurityConfig;
import com.zakytok.mediacatalogservice.domain.ItemNotUniqueException;
import com.zakytok.mediacatalogservice.domain.ItemService;
import com.zakytok.mediacatalogservice.domain.ItemType;
import com.zakytok.mediacatalogservice.domain.ItemValid;
import com.zakytok.mediacatalogservice.web.ItemController;
import com.zakytok.mediacatalogservice.web.ItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@Import(SecurityConfig.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtDecoder jwtDecoder;

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
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority("ROLE_employee")))
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
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority("ROLE_employee")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(toCreate)))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(exceptionMessage));
    }

    @Test
    void whenDeleteItemWithAdminRoleThen204() throws Exception {
        var itemId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/items/" + itemId)
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority("ROLE_employee"))))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void whenDeleteItemWithUserRoleThen403() throws Exception {
        var itemId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/items/" + itemId)
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority("ROLE_customer"))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void whenDeleteItemWithUserRoleThen401() throws Exception {
        var itemId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/items/" + itemId))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}