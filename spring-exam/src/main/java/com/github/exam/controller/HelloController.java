package com.github.exam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // Simple. Direct. No business logic in controllers.
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}