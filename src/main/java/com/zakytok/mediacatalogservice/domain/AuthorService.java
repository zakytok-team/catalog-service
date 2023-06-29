package com.zakytok.mediacatalogservice.domain;

import com.zakytok.mediacatalogservice.web.AuthorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    Set<Author> findAllAuthors(Set<UUID> authorIds) {
        return authorIds.stream().map(this::find).collect(toSet());
    }

    Author find(UUID id) {
        return authorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Author with id " + id + " does not exist."));
    }

    public AuthorDto create(AuthorDto author) {
        Author toSave = Author.of(author.name(), author.realName(), author.info(), author.type());
        authorRepository.save(toSave);
        return authorMapper.toDto(toSave);
    }

    public List<AuthorDto> getAll() {
        return authorMapper.allToDto(authorRepository.findAll());
    }

    public void delete(UUID id) {
        Author toDelete = find(id);
        authorRepository.delete(toDelete);
    }
}
