package com.zakytok.catalogservice.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String author;
    private int year;

    @Enumerated(EnumType.STRING)
    private ItemType type;
    @Enumerated(EnumType.STRING)
    private ItemValid valid;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;
    @Version
    private int version;

    private Item(String title, String author, int year, ItemType type, ItemValid valid) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.type = type;
        this.valid = valid;
    }

    public static Item of(String title, String author, int year, ItemType type, ItemValid valid) {
        return new Item(title, author, year, type, valid);
    }
}

