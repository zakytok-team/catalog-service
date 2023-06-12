package com.zakytok.catalogservice.web;

import com.zakytok.catalogservice.domain.genre.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<GenreDto>> getGenres() {
        List<GenreDto> genres = genreService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(genres);
    }
}
