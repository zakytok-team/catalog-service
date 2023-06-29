package com.zakytok.mediacatalogservice.domain;

import com.zakytok.mediacatalogservice.web.MediaDto;
import com.zakytok.mediacatalogservice.web.MediaValidDto;
import com.zakytok.mediacatalogservice.web.TrackDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final GenreService genreService;
    private final AuthorService authorService;
    private final LabelService labelService;
    private final TrackRepository trackRepository;

    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;

    public MediaDto get(UUID id) {
        Media media = findMedia(id);
        return mediaMapper.toDto(media);
    }

    public MediaValidDto isValid(UUID id) {
        Optional<Media> media = mediaRepository.findById(id);
        if (media.isPresent()) {
            boolean valid = media.get().getValid().equals(MediaValid.VALID);
            return MediaValidDto.of(id, valid);
        } else {
            return MediaValidDto.of(id, false);
        }
    }

    public List<MediaDto> getAll() {
        Iterable<Media> allMedia = mediaRepository.findAll();
        return mediaMapper.allToDtos(allMedia);
    }

    public MediaDto updateGenres(UUID id, Set<String> genresNames) {
        Media toUpdate = findMedia(id);
        Set<Genre> genres = genreService.findAll(genresNames);
        toUpdate.setGenres(genres);
        return mediaMapper.toDto(toUpdate);
    }

    @Transactional
    public MediaDto create(MediaDto mediaDto) {
        if (isUnique(mediaDto)) {
            Media saved = mediaRepository.save(build(mediaDto));
            fillWithTracks(mediaDto.tracks(), saved);

            return mediaMapper.toDto(saved);
        } else {
            throw new MediaNotUniqueException("Media " + mediaDto + " is not unique!");
        }
    }

    public void delete(UUID id) {
        Media toDelete = findMedia(id);
        mediaRepository.delete(toDelete);
    }

    private boolean isUnique(MediaDto media) {
        return !mediaRepository.existsByTitleAndAuthorIdAndLabelIdAndYearAndType(media.title(), media.authorId(), media.labelId(), media.year(), media.type());
    }

    private Media findMedia(UUID id) {
        return mediaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Media with id " + id + " not exists!"));
    }

    private Media build(MediaDto mediaDto) {
        Set<Genre> genres = genreService.findAll(mediaDto.genres());
        Author author = authorService.find(mediaDto.authorId());
        Label label = labelService.find(mediaDto.labelId());

        return Media.of(mediaDto.title(), author, label, mediaDto.year(), mediaDto.type(), MediaValid.VALID, genres);
    }

    private void fillWithTracks(Set<TrackDto> tracks, Media media) {
        Set<Track> toSave = tracks.stream()
                .map(track -> buildTrack(media, track))
                .collect(toSet());
        media.setTracks(toSave);
        trackRepository.saveAll(toSave);
    }

    private Track buildTrack(Media media, TrackDto el) {
        return Track.of(el.name(), el.position(), el.secondsDuration(), media,
                authorService.findAllAuthors(el.authorIds()));
    }
}
