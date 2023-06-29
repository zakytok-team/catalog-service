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
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private int yearCreated;

    @OneToMany(mappedBy = "label")
    private Set<Media> mediaSet;

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

    public Label(String name, int yearCreated) {
        this.name = name;
        this.yearCreated = yearCreated;
    }

    public static Label of(String name, int yearCreated) {
        return new Label(name, yearCreated);
    }
}
