package com.zakytok.catalogservice.web;

import com.zakytok.catalogservice.domain.ItemType;
import com.zakytok.catalogservice.domain.ItemValid;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ItemDto {
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
