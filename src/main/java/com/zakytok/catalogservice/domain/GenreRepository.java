package com.zakytok.catalogservice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    boolean existsByName(String name);

    Optional<Genre> findByName(String name);

    List<Genre> findAllByParentIdIsNull();
}
