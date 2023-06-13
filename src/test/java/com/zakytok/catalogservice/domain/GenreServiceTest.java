package com.zakytok.catalogservice.domain;

import com.zakytok.catalogservice.web.GenreDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

    @Mock
    GenreRepository genreRepository;

    @Mock
    GenreMapper genreMapper;

    @InjectMocks
    GenreServiceImpl genreService;

    @Test
    void createNewParentGenre() {
        // dto
        // create genre:
        // {
        //  genre:
        //      parent_id may be or null, if no then itself is parent;
        //      String name;
        //      genre: {
        //      }
        // }
        // return our genre with all childs
        GenreDto toCreate = GenreDto.of("techno");
        Genre expected = Genre.of("techno");
        GenreDto expectedDto = GenreDto.of("techno");
        when(genreRepository.save(any())).thenReturn(expected);
        when(genreMapper.toDto(expected)).thenReturn(expectedDto);

        GenreDto created = genreService.create(toCreate);

        assertThat(created).matches(genre ->
                genre.name().equals(expected.getName()) && genre.parentId() == null);
    }

    @Test
    void createChildGenre() {
        GenreDto toCreate = GenreDto.of(1L, "techno");
        Genre parent = Genre.builder().id(1L).name("electronic").build();
        Genre expected = Genre.of(parent, "techno");
        GenreDto expectedDto = GenreDto.of(parent.getId(), "techno");
        when(genreRepository.findById(1L)).thenReturn(Optional.of(parent));
        when(genreRepository.save(any())).thenReturn(expected);
        when(genreMapper.toDto(expected)).thenReturn(expectedDto);

        GenreDto created = genreService.create(toCreate);

        assertThat(created).matches(genre ->
                genre.name().equals(expected.getName()) && Objects.equals(genre.parentId(), expected.getParent().getId()));
    }

    @Test
    void createWithInvalidParent() {
        GenreDto toCreate = GenreDto.of(-1L, "techno");

        when(genreRepository.findById(-1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> genreService.create(toCreate))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Genre with id " + toCreate.parentId() + " does not exist.");
    }

    @Test
    void createNotUniqueGenre() {
        GenreDto toCreate = GenreDto.of("techno");

        when(genreRepository.existsByName(toCreate.name())).thenReturn(true);

        assertThatThrownBy(() -> genreService.create(toCreate))
                .isInstanceOf(GenreNotUniqueException.class)
                .hasMessage("Genre " + toCreate.name() + " already exists!");
    }

    @Test
    void updateWithNotExistingGenre() {
        Long invalidId = 1L;
        String newValue = "house";

        when(genreRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> genreService.update(invalidId, newValue))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Genre with id " + invalidId + " does not exist.");
    }

    @Test
    void updateWithNotUniqueGenre() {
        Long invalidId = 1L;
        String newValue = "house";

        Genre toUpdate = Genre.of("techno");

        when(genreRepository.findById(invalidId)).thenReturn(Optional.of(toUpdate));
        when(genreRepository.existsByName(newValue)).thenReturn(true);

        assertThatThrownBy(() -> genreService.update(invalidId, newValue))
                .isInstanceOf(GenreNotUniqueException.class)
                .hasMessage("Genre " + newValue + " already exists!");
    }
}
