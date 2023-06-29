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
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String realName;
    private String info;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private AuthorType type;

    @OneToMany(mappedBy = "author")
    private Set<Media> mediaSet;

    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;
    @org.springframework.data.annotation.Version
    private int version;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String lastModifiedBy;

    public Author(String name, String realName, String info, AuthorType type) {
        this.name = name;
        this.realName = realName;
        this.info = info;
        this.type = type;
    }

    public static Author of(String name, String realName, String info, AuthorType type) {
        return new Author(name, realName, info, type);
    }
}
