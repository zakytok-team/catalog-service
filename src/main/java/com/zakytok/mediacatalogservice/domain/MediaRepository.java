package com.zakytok.mediacatalogservice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MediaRepository extends CrudRepository<Media, UUID> {
    boolean existsByTitleAndAuthorIdAndLabelIdAndYearAndType(String title, UUID authorId, UUID labelId, int year, MediaType type);
}
