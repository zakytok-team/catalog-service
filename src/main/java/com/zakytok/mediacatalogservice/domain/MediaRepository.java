package com.zakytok.mediacatalogservice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MediaRepository extends CrudRepository<Media, UUID> {
    boolean existsByTitleAndAuthorAndYearAndType(String title, String author, int year, MediaType type);
}
