package com.zakytok.catalogservice.web;

import com.zakytok.catalogservice.domain.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems() {
        List<ItemDto> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto item) {
        ItemDto itemDto = itemService.create(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemDto);
    }

    @PatchMapping("{id}/genres")
    public ResponseEntity<ItemDto> updateItemGenres(@PathVariable UUID id, @RequestParam("names") Set<String> genres) {
        ItemDto updated = itemService.updateItemGenres(id, genres);
        return ResponseEntity.ok(updated);
    }
}
