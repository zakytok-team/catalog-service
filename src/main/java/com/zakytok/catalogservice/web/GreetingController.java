package com.zakytok.catalogservice.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @Value("${zakytok.greeting}")
    String greetingMessage;

    @GetMapping
    public String greeting() {
        return greetingMessage;
    }
}
