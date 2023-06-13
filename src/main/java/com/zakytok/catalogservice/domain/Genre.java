package com.zakytok.catalogservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Genre parent;

    private String name;

    @OneToMany(mappedBy = "parent")
    private Set<Genre> children;

    @ManyToMany(mappedBy = "genres")
    private Set<Item> items;

    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;
    @org.springframework.data.annotation.Version
    private int version;

    public Genre(String name) {
        this.name = name;
    }

    public Genre(Genre parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public static Genre of(String name) {
        return new Genre(name);
    }

    public static Genre of(Genre parent, String name) {
        return new Genre(parent, name);
    }
}
