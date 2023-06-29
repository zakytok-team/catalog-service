package com.zakytok.mediacatalogservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String position;
    private int secondsDuration;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

    @ManyToMany
    @JoinTable(name = "track_author",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;
    @org.springframework.data.annotation.Version
    private int version;
    @CreatedBy
    String createdBy;
    @LastModifiedBy
    String lastModifiedBy;

    public Track(String name, String position, int secondsDuration, Media media, Set<Author> authors) {
        this.name = name;
        this.position = position;
        this.secondsDuration = secondsDuration;
        this.media = media;
        this.authors = authors;
    }

    public static Track of(String name, String position, int secondsDuration, Media media, Set<Author> authors) {
        return new Track(name, position, secondsDuration, media, authors);
    }
}
