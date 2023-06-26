package com.zakytok.mediacatalogservice.web;

import com.zakytok.mediacatalogservice.domain.MediaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaDto> get(@PathVariable UUID id) {
        MediaDto media = mediaService.get(id);
        return ResponseEntity.ok(media);
    }

    @GetMapping("/{id}/valid")
    public ResponseEntity<MediaValidDto> isMediaValid(@PathVariable UUID id) {
        MediaValidDto mediaValid = mediaService.isValid(id);
        return ResponseEntity.ok(mediaValid);
    }

    @GetMapping
    public ResponseEntity<List<MediaDto>> getAll() {
        List<MediaDto> allMedia = mediaService.getAll();
        return ResponseEntity.ok(allMedia);
    }

    @PostMapping
    public ResponseEntity<MediaDto> create(@RequestBody MediaDto toCreate) {
        MediaDto mediaDto = mediaService.create(toCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(mediaDto);
    }

    @PatchMapping("/{id}/genres")
    public ResponseEntity<MediaDto> updateMediaGenres(@PathVariable UUID id, @RequestParam("names") Set<String> genres) {
        MediaDto updated = mediaService.updateMediaGenres(id, genres);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        mediaService.delete(id);
    }
}
