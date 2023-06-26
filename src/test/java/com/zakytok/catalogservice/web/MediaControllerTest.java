package com.zakytok.catalogservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zakytok.mediacatalogservice.config.SecurityConfig;
import com.zakytok.mediacatalogservice.domain.MediaNotUniqueException;
import com.zakytok.mediacatalogservice.domain.MediaService;
import com.zakytok.mediacatalogservice.domain.MediaType;
import com.zakytok.mediacatalogservice.domain.MediaValid;
import com.zakytok.mediacatalogservice.web.MediaController;
import com.zakytok.mediacatalogservice.web.MediaDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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

@WebMvcTest(MediaController.class)
@Import(SecurityConfig.class)
public class MediaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtDecoder jwtDecoder;

    @MockBean
    MediaService mediaService;

    @Test
    void getAllItemsTestSuccessful() throws Exception {
        List<MediaDto> items = List.of(MediaDto.of("title1", "author1", 1989, MediaType.VINYL, Set.of("techno")));
        when(mediaService.getAll()).thenReturn(items);

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
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
        MediaDto toCreate = MediaDto.of("title1", "author1", 1989, MediaType.VINYL, Set.of("techno"));
        MediaDto created = MediaDto.of("title1", "author1", 1989, MediaType.VINYL, MediaValid.VALID, Set.of("techno"));

        when(mediaService.create(any())).thenReturn(created);

        mockMvc.perform(post("/items")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority("ROLE_employee")))
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(created)));
    }

    @Test
    void createItemNotUnique() throws Exception {
        MediaDto toCreate = MediaDto.of("title1", "author1", 1989, MediaType.VINYL, Set.of("techno"));
        String exceptionMessage = "Item " + toCreate + " not unique!";
        when(mediaService.create(toCreate)).thenThrow(new MediaNotUniqueException(exceptionMessage));

        mockMvc.perform(post("/items")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority("ROLE_employee")))
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
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