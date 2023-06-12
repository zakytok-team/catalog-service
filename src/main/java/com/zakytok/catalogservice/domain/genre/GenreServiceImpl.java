package com.zakytok.catalogservice.domain.genre;

import com.zakytok.catalogservice.web.GenreDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    public GenreDto create(GenreDto toCreate) {
        if (genreRepository.existsByName(toCreate.name())) {
            throw new GenreNotUniqueException("Genre " + toCreate.name() + " already exists!");
        }
        Genre created = genreRepository.save(build(toCreate));
        return genreMapper.toDto(created);
    }

    public List<GenreDto> getAll() {
        Iterable<Genre> genres = genreRepository.findAll();
        return genreMapper.allToDtos(genres);
    }

    private Genre build(GenreDto newGenre) {
        if (newGenre.parentId() == null) {
            return Genre.of(newGenre.name());
        } else {
            Genre parent = genreRepository.findById(newGenre.parentId())
                    .orElseThrow(() -> new NoSuchElementException("Genre with parent id " + newGenre.parentId() + " does not exist."));
            return Genre.of(parent, newGenre.name());
        }
    }
}
