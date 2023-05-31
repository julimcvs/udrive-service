package com.si.udriveservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/test")
public class TestController {
    @GetMapping
    ResponseEntity<Integer> integer() {
        log.info("REST to test the sum of 2 integers");
        return ResponseEntity.ok(Integer.sum(1, 2));
    }
}
