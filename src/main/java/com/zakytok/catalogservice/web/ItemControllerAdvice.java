package com.zakytok.catalogservice.web;

import com.zakytok.catalogservice.domain.item.ItemNotUniqueException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class ItemControllerAdvice {

    @ExceptionHandler(ItemNotUniqueException.class)
    public ResponseEntity<String> handleItemNotUniqueException(ItemNotUniqueException ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}