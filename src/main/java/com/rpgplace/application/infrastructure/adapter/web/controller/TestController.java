package com.rpgplace.application.infrastructure.adapter.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping
    public ResponseEntity<String> test() {
        log.info("Test endpoint was called successfully!");
        return ResponseEntity.ok("RPGPlace API is running!");
    }
}
