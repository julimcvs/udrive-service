package com.si.udriveservice.controller;

import com.si.udriveservice.model.dto.UserDTO;
import com.si.udriveservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") Long id) {
        log.info("REST to delete User by id: {id}");
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<UserDTO> edit(@RequestBody @Valid UserDTO dto) {
        log.info("REST to save User");
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable(name = "id") Long id) {
        log.info("REST to find User by id: {id}");
        return ResponseEntity.ok(service.findById(id));
    }
}
