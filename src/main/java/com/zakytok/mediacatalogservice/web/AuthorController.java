package com.zakytok.mediacatalogservice.web;

import com.zakytok.mediacatalogservice.domain.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("catalog/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorDto> create(@RequestBody AuthorDto author) {
        AuthorDto created = authorService.create(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDto>> get() {
        List<AuthorDto> genres = authorService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(genres);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        authorService.delete(id);
    }
}
