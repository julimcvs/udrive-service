package com.si.udriveservice.controller;

import com.si.udriveservice.model.dto.UserDTO;
import com.si.udriveservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@RequestParam Long id) {
        log.info("REST to delete User by id: {id}");
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@RequestParam Long id) {
        log.info("REST to find User by id: {id}");
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody @Valid UserDTO dto) {
        log.info("REST to save User");
        return ResponseEntity.ok(service.save(dto));
    }
}
