package com.zakytok.catalogservice.domain;

import com.zakytok.mediacatalogservice.domain.*;
import com.zakytok.mediacatalogservice.web.MediaDto;
import com.zakytok.mediacatalogservice.web.MediaValidDto;
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
public class MediaServiceTest {

    @Mock
    MediaRepository mediaRepository;

    @Mock
    GenreRepository genreRepository;

    @Mock
    MediaMapper mediaMapper;

    @InjectMocks
    MediaServiceImpl itemService;

    @Test
    void createItem() {
        MediaDto toCreate = MediaDto.of("title1", "author1", 1989, MediaType.VINYL, Set.of("techno"));
        when(mediaRepository.existsByTitleAndAuthorAndYearAndType(toCreate.title(), toCreate.author(), toCreate.year(), toCreate.type()))
                .thenReturn(false);
        when(genreRepository.findByName("techno")).thenReturn(Optional.of(Genre.of("techno")));

        itemService.create(toCreate);

        verify(mediaRepository).save(any());
    }

    @Test
    void createItemNotUnique() {
        MediaDto toCreate = MediaDto.of("title1", "author1", 1989, MediaType.VINYL, Set.of("techno"));
        when(mediaRepository.existsByTitleAndAuthorAndYearAndType(
                toCreate.title(),
                toCreate.author(),
                toCreate.year(),
                toCreate.type())).thenReturn(true);

        assertThatThrownBy(() -> itemService.create(toCreate))
                .isInstanceOf(MediaNotUniqueException.class)
                .hasMessage("Item " + toCreate + " is not unique!");
    }

    @Test
    void itemNotValid() {
        UUID itemId = UUID.randomUUID();
        Media notValidMedia = new Media();
        notValidMedia.setValid(MediaValid.INVALID);

        when(mediaRepository.findById(itemId)).thenReturn(Optional.of(notValidMedia));

        MediaValidDto itemValid = itemService.isValid(itemId);

        assert !itemValid.isValid();
    }

    @Test
    void itemValid() {
        UUID itemId = UUID.randomUUID();
        Media notValidMedia = new Media();
        notValidMedia.setValid(MediaValid.VALID);

        when(mediaRepository.findById(itemId)).thenReturn(Optional.of(notValidMedia));

        MediaValidDto itemValid = itemService.isValid(itemId);

        assert itemValid.isValid();
    }

    @Test
    void uploadImageToAWS() {
    }
}
