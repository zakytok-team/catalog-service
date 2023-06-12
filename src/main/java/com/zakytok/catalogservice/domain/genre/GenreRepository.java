package com.zakytok.catalogservice.domain.genre;

import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    boolean existsByName(String name);
}
