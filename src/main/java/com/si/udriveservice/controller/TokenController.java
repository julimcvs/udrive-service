package com.si.udriveservice.controller;

import com.si.udriveservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
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