package com.zakytok.mediacatalogservice.domain;

import com.zakytok.mediacatalogservice.web.MediaDto;
import com.zakytok.mediacatalogservice.web.MediaValidDto;
import com.zakytok.mediacatalogservice.web.TrackDto;
import org.jetbrains.annotations.NotNull;
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
    AuthorRepository authorRepository;

    @Mock
    LabelRepository labelRepository;

    @Mock
    TrackRepository trackRepository;

    @Mock
    MediaMapper mediaMapper;

    @InjectMocks
    MediaService itemService;

    @Test
    void createMedia() {
        MediaDto toCreate = buildTestMedia();
        when(mediaRepository.existsByTitleAndAuthorIdAndLabelIdAndYearAndType(toCreate.title(), toCreate.authorId(), toCreate.labelId(), toCreate.year(), toCreate.type()))
                .thenReturn(false);
        when(genreRepository.findByName("techno")).thenReturn(Optional.of(Genre.of("techno")));
        when(authorRepository.findById(any())).thenReturn(Optional.of(new Author()));
        when(labelRepository.findById(any())).thenReturn(Optional.of(new Label()));
       // when(trackRepository.save(any())).thenReturn(Optional.of(new Track()));

        itemService.create(toCreate);

        verify(mediaRepository).save(any());
    }

    @Test
    void createMediaNotUnique() {
        MediaDto toCreate = buildTestMedia();
        when(mediaRepository.existsByTitleAndAuthorIdAndLabelIdAndYearAndType(
                toCreate.title(),
                toCreate.authorId(),
                toCreate.labelId(),
                toCreate.year(),
                toCreate.type())).thenReturn(true);

        assertThatThrownBy(() -> itemService.create(toCreate))
                .isInstanceOf(MediaNotUniqueException.class)
                .hasMessage("Media " + toCreate + " is not unique!");
    }

    @NotNull
    private static MediaDto buildTestMedia() {
        UUID authorId = UUID.randomUUID();
        UUID labelId = UUID.randomUUID();
        TrackDto track = TrackDto.of( "Om namo", "A1", 245, Set.of(authorId));
        String genre = "techno";
        return MediaDto.of("Silent Call", authorId, labelId, 1989, MediaType.VINYL, Set.of(genre), Set.of(track));
    }

    @Test
    void mediaNotValid() {
        UUID mediaId = UUID.randomUUID();
        Media notValidMedia = new Media();
        notValidMedia.setValid(MediaValid.INVALID);

        when(mediaRepository.findById(mediaId)).thenReturn(Optional.of(notValidMedia));

        MediaValidDto itemValid = itemService.isValid(mediaId);

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
