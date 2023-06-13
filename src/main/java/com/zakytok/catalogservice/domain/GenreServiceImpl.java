package com.zakytok.catalogservice.domain;

import com.zakytok.catalogservice.web.GenreDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    @Transactional
    public GenreDto create(GenreDto toCreate) {
        existsByName(toCreate.name());
        Genre created = genreRepository.save(build(toCreate));
        return genreMapper.toDto(created);
    }

    public GenreDto get(String name) {
        Genre genre = find(name);
        return genreMapper.toDto(genre);
    }

    public GenreDto get(Long id) {
        Genre genre = find(id);
        return genreMapper.toDto(genre);
    }

    public Set<GenreDto> getAll() {
        Iterable<Genre> genres = genreRepository.findAllByParentIdIsNull();
        return genreMapper.allToDtos(genres);
    }

    @Transactional
    public GenreDto update(Long id, String newValue) {
        Genre toUpdate = find(id);
        existsByName(newValue);
        toUpdate.setName(newValue);
        return genreMapper.toDto(toUpdate);
    }

    @Transactional
    public void delete(Long id) {
        Genre toDelete = find(id);
        genreRepository.delete(toDelete);
    }

    private Genre find(Long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new NoSuchElementException("Genre with id " + genreId + " does not exist."));
    }

    private Genre find(String name) {
        return genreRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Genre with name " + name + " does not exist."));
    }

    private void existsByName(String name) {
        if (genreRepository.existsByName(name)) {
            throw new GenreNotUniqueException("Genre " + name + " already exists!");
        }
    }

    private Genre build(GenreDto newGenre) {
        if (newGenre.parentId() == null) {
            return Genre.of(newGenre.name());
        } else {
            Genre parent = find(newGenre.parentId());
            return Genre.of(parent, newGenre.name());
        }
    }
}
