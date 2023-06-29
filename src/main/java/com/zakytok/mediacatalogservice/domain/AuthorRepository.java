package com.zakytok.mediacatalogservice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AuthorRepository extends CrudRepository<Author, UUID> {
}