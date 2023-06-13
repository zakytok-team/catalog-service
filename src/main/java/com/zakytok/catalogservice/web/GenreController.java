package com.zakytok.catalogservice.web;

import com.zakytok.catalogservice.domain.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<GenreDto> createGenre(@RequestBody GenreDto genre) {
        GenreDto created = genreService.create(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<Set<GenreDto>> getGenres() {
        Set<GenreDto> genres = genreService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(genres);
    }

    @PatchMapping("{id}")
    public ResponseEntity<GenreDto> updateGenre(@PathVariable Long id, @RequestParam String newName) {
        GenreDto updated = genreService.update(id, newName);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable Long id) {
        genreService.delete(id);
    }
}
