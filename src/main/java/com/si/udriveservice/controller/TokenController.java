package com.si.udriveservice.controller;

import com.si.udriveservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/token")
public class TokenController {
    private final TokenService service;

    @GetMapping("/{token}")
    public ResponseEntity<Void> checkTokenValid(@PathVariable String token) {
        log.info("REST to check if token {} is valid", token);
        service.checkValidToken(token);
        return ResponseEntity.ok().build();
    }
}