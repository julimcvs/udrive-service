package com.si.udriveservice.controller;

import com.si.udriveservice.model.record.UniversityDTO;
import com.si.udriveservice.service.UniversityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/university")
public class UniversityController {
    private final UniversityService service;

    @GetMapping
    private ResponseEntity<List<UniversityDTO>> findAll() {
        log.info("REST to return all active universities.");
        return ResponseEntity.ok(service.findAll());
    }
}
