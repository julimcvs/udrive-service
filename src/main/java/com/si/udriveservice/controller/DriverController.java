package com.si.udriveservice.controller;

import com.si.udriveservice.model.dto.DriverDTO;
import com.si.udriveservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService service;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@RequestParam Long id) {
        log.info("REST to delete Driver by id: {id}");
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> findById(@RequestParam Long id) {
        log.info("REST to find Driver by id: {id}");
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DriverDTO> save(@RequestBody @Valid DriverDTO dto) {
        log.info("REST to save Driver");
        return ResponseEntity.ok(service.save(dto));
    }
}
