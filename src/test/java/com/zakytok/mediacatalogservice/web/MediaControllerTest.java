package com.zakytok.mediacatalogservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zakytok.mediacatalogservice.config.SecurityConfig;
import com.zakytok.mediacatalogservice.domain.MediaNotUniqueException;
import com.zakytok.mediacatalogservice.domain.MediaService;
import com.zakytok.mediacatalogservice.domain.MediaType;
import com.zakytok.mediacatalogservice.domain.MediaValid;
import org.jetbrains.annotations.NotNull;
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
    void getAllMediaTestSuccessful() throws Exception {
        List<MediaDto> items = List.of(buildTestMedia());
        when(mediaService.getAll()).thenReturn(items);

        mockMvc.perform(get("/media"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(items)));
    }

    @Test
    void createMedia() throws Exception {
        MediaDto toCreate = buildTestMedia();
        MediaDto created = buildTestCreatedMedia();

        when(mediaService.create(any())).thenReturn(created);

        mockMvc.perform(post("/media")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority("ROLE_employee")))
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(created)));
    }

    @Test
    void createMediaNotUnique() throws Exception {
        MediaDto toCreate = buildTestMedia();
        String exceptionMessage = "Media " + toCreate + " not unique!";
        when(mediaService.create(toCreate)).thenThrow(new MediaNotUniqueException(exceptionMessage));

        mockMvc.perform(post("/media")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority("ROLE_employee")))
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(toCreate)))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(exceptionMessage));
    }

    @Test
    void whenDeleteMediaWithAdminRoleThen204() throws Exception {
        var mediaId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/media/" + mediaId)
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority("ROLE_employee"))))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void whenDeleteMediaWithUserRoleThen403() throws Exception {
        var mediaId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/media/" + mediaId)
                        .with(SecurityMockMvcRequestPostProcessors.jwt()
                                .authorities(new SimpleGrantedAuthority("ROLE_customer"))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void whenDeleteMediaWithUserRoleThen401() throws Exception {
        var itemId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/media/" + itemId))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @NotNull
    private static MediaDto buildTestMedia() {
        UUID authorId = UUID.randomUUID();
        UUID labelId = UUID.randomUUID();
        TrackDto track = TrackDto.of( "Om namo", "A1", 245, Set.of(authorId));
        String genre = "techno";
        return MediaDto.of("Silent Call", authorId, labelId, 1989, MediaType.VINYL, Set.of(genre), Set.of(track));
    }

    @NotNull
    private static MediaDto buildTestCreatedMedia() {
        UUID authorId = UUID.randomUUID();
        UUID labelId = UUID.randomUUID();
        TrackDto track = TrackDto.of( "Om namo", "A1", 245, Set.of(authorId));
        String genre = "techno";
        return MediaDto.of("Silent Call", authorId, labelId, 1989, MediaType.VINYL, MediaValid.VALID, Set.of(genre), Set.of(track));
    }
}
