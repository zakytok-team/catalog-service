package com.zakytok.mediacatalogservice.domain;

import com.zakytok.mediacatalogservice.web.MediaDto;
import com.zakytok.mediacatalogservice.web.MediaValidDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;
    private final GenreRepository genreRepository;

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

    public MediaDto updateMediaGenres(UUID id, Set<String> genresNames) {
        Media toUpdate = findMedia(id);
        Set<Genre> genres = mapGenres(genresNames);
        toUpdate.setGenres(genres);
        return mediaMapper.toDto(toUpdate);
    }

    private Media findMedia(UUID id) {
        return mediaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Media with id " + id + " not exists!"));
    }

    public MediaDto create(MediaDto mediaDto) {
        if (isUnique(mediaDto)) {
            Media toSave = build(mediaDto);
            Media saved = mediaRepository.save(toSave);
            return mediaMapper.toDto(saved);
        } else {
            throw new MediaNotUniqueException("Media " + mediaDto + " is not unique!");
        }
    }

    @Override
    public void delete(UUID id) {
        Media toDelete = findMedia(id);
        mediaRepository.delete(toDelete);
    }

    private boolean isUnique(MediaDto media) {
        return !mediaRepository.existsByTitleAndAuthorAndYearAndType(media.title(), media.author(), media.year(), media.type());
    }

    private Media build(MediaDto mediaDto) {
        Set<Genre> genres = mapGenres(mediaDto.genres());
        return Media.of(mediaDto.title(), mediaDto.author(), mediaDto.year(), mediaDto.type(), MediaValid.VALID, genres);
    }

    private Set<Genre> mapGenres(Set<String> names) {
        return names.stream().map(name ->
                genreRepository.findByName(name)
                        .orElseThrow(() -> new NoSuchElementException("Genre with name " + name + " does not exist."))
        ).collect(toSet());
    }
}
