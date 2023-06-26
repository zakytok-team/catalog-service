package com.zakytok.mediacatalogservice.web;

import com.zakytok.mediacatalogservice.domain.MediaNotUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class MediaControllerAdvice {

    @ExceptionHandler(MediaNotUniqueException.class)
    public ResponseEntity<String> handleMediaNotUniqueException(MediaNotUniqueException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}