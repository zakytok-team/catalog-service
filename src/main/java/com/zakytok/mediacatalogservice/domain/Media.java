package com.zakytok.mediacatalogservice.domain;

import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Version;
import org.springframework.data.annotation.*;
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
    private int year;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "label_id")
    private Label label;

    @OneToMany(mappedBy = "media")
    private Set<Track> tracks;

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

    private Media(String title, Author author, Label label, int year, MediaType type, MediaValid valid, Set<Genre> genres) {
        this.title = title;
        this.author = author;
        this.label = label;
        this.year = year;
        this.type = type;
        this.valid = valid;
        this.genres = genres;
    }

    public static Media of(String title, Author author, Label label, int year, MediaType type, MediaValid valid, Set<Genre> genres) {
        return new Media(title, author, label, year, type, valid, genres);
    }
}

