package com.zakytok.mediacatalogservice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ItemRepository extends CrudRepository<Item, UUID> {
    boolean existsByTitleAndAuthorAndYearAndType(String title, String author, int year, ItemType type);
}
