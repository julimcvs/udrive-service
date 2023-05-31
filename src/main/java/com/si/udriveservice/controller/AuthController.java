package com.si.udriveservice.controller;

import com.si.udriveservice.model.dto.AuthDTO;
import com.si.udriveservice.model.dto.LoginDTO;
import com.si.udriveservice.model.dto.PasswordDTO;
import com.si.udriveservice.model.dto.UserDTO;
import com.si.udriveservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthDTO> authenticate(@RequestBody LoginDTO dto) {
        log.info("REST to authenticate a user");
        return ResponseEntity.ok(service.authenticate(dto));
    }

    @PostMapping
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO dto) {
        log.info("REST to save User");
        return ResponseEntity.ok(service.register(dto));
    }

    @PostMapping("/save-password")
    public ResponseEntity<UserDTO> savePassword(@RequestBody @Valid PasswordDTO dto) {
        log.info("REST to change the user's default password");
        return ResponseEntity.ok(service.savePassword(dto));
    }
}
