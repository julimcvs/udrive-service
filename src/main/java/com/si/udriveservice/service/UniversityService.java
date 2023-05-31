package com.si.udriveservice.service;

import com.si.udriveservice.configuration.BusinessRuleException;
import com.si.udriveservice.model.enums.StatusEnum;
import com.si.udriveservice.model.record.UniversityDTO;
import com.si.udriveservice.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniversityService {
    public static final String UNIVERSITY_EXCEPTION = "university.exception";
    public static final String ENTITY_NOT_FOUND = "entity.not.found.female";
    public final UniversityRepository repository;

    public boolean existsById(Long id) {
        return repository.existsByIdAndStatus(id, StatusEnum.ACTIVE);
    }

    public List<UniversityDTO> findAll() {
        return repository.findAllByStatus(StatusEnum.ACTIVE)
                .stream()
                .map(university -> new UniversityDTO(university.getId(), university.getName()))
                .toList();
    }

    private BusinessRuleException getException(String message) {
        return new BusinessRuleException(UNIVERSITY_EXCEPTION, message);
    }

    private BusinessRuleException getException(String message, String value) {
        return new BusinessRuleException(UNIVERSITY_EXCEPTION, message, value);
    }
}
