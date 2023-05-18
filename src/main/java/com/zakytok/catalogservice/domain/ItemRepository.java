package com.zakytok.catalogservice.domain;

import com.zakytok.catalogservice.web.ItemDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long> {

    @Query("SELECT * FROM item")
    List<ItemDto> getAllDtos();

    @Query("SELECT * from item WHERE title = (:title) " +
            "AND author = (:author) " +
            "AND year = (:year) " +
            "AND type = (:type)")
    Optional<Item> findBy(String title, String author, int year, ItemType type);
}
