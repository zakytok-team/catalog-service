package com.zakytok.catalogservice.domain.item;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ItemRepository extends CrudRepository<Item, UUID> {
    boolean existsByTitleAndAuthorAndYearAndType(String title, String author, int year, ItemType type);
}
