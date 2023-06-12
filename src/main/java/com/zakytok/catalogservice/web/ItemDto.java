package com.zakytok.catalogservice.web;

import com.zakytok.catalogservice.domain.item.ItemType;
import com.zakytok.catalogservice.domain.item.ItemValid;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ItemDto {
    private UUID id;
    private String title;
    private String author;
    private int year;
    private ItemType type;
    private ItemValid valid;

    public ItemDto(String title, String author, int year, ItemType type) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.type = type;
    }
}
