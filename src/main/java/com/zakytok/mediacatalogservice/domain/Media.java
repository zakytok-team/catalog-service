package com.zakytok.mediacatalogservice.domain;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String author;
    private int year;
    @ManyToMany
    @JoinTable(
            name = "media_genre",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;
    @Enumerated(EnumType.STRING)
    private MediaType type;
    @Enumerated(EnumType.STRING)
    private MediaValid valid;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;
    @Version
    private int version;
    @CreatedBy
    String createdBy;
    @LastModifiedBy
    String lastModifiedBy;

    private Media(String title, String author, int year, MediaType type, MediaValid valid, Set<Genre> genres) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.type = type;
        this.valid = valid;
        this.genres = genres;
    }

    public static Media of(String title, String author, int year, MediaType type, MediaValid valid, Set<Genre> genres) {
        return new Media(title, author, year, type, valid, genres);
    }
}

