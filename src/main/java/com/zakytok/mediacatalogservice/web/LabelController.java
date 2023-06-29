package com.zakytok.mediacatalogservice.web;

import com.zakytok.mediacatalogservice.domain.LabelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("catalog/labels")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @PostMapping
    public ResponseEntity<LabelDto> create(@RequestBody LabelDto label) {
        LabelDto created = labelService.create(label);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<LabelDto>> get() {
        List<LabelDto> genres = labelService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(genres);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        labelService.delete(id);
    }
}
