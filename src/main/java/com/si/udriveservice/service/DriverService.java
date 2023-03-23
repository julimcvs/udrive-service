package com.si.udriveservice.service;

import com.si.udriveservice.configuration.BusinessRuleException;
import com.si.udriveservice.model.dto.BasicIdDTO;
import com.si.udriveservice.model.dto.DriverDTO;
import com.si.udriveservice.model.entity.Driver;
import com.si.udriveservice.model.entity.Driver_;
import com.si.udriveservice.model.enums.StatusEnum;
import com.si.udriveservice.model.mapper.DriverMapper;
import com.si.udriveservice.repository.BaseSpecs;
import com.si.udriveservice.repository.DriverRepository;
import com.si.udriveservice.service.utils.ServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DriverService implements BaseSpecs<Driver> {
    public static final String DRIVER_EXCEPTION = "driver.exception";
    public static final String ENTITY_NOT_FOUND = "entity.not.found.male";
    public static final String DRIVER = "entity.driver";
    private final DriverRepository repository;
    private final DriverMapper mapper;
    private final UniversityService universityService;

    @Transactional
    public void deleteById(Long id) {
        Driver entity = findEntityById(id);
        entity.setStatus(StatusEnum.INACTIVE);
        repository.save(entity);
    }

    public DriverDTO findById(Long id) {
        return mapper.toDto(findEntityById(id));
    }

    public Driver findEntityById(Long id) {
        return repository.findByIdAndStatus(id, StatusEnum.ACTIVE)
                .orElseThrow(() -> getException(ENTITY_NOT_FOUND, DRIVER));
    }

    @Transactional
    public DriverDTO save(DriverDTO dto) {
        validateDto(dto);
        Driver entity = mapper.toEntity(dto);
        Driver savedEntity = findSavedEntity(dto.getId());
        if (savedEntity != null) {
            entity.setCpf(savedEntity.getCpf());
            if (Objects.equals(savedEntity.getUniversity().getId(),
                    dto.getUniversity().getId())) {
                entity.setRegistrationNumber(savedEntity.getRegistrationNumber());
            }
        }
        entity.setStatus(StatusEnum.ACTIVE);
        return mapper.toDto(repository.save(entity));
    }

    private Driver findSavedEntity(Long id) {
        if (id == null) {
            return null;
        }
        return findEntityById(id);
    }

    private BusinessRuleException getException(String message) {
        return new BusinessRuleException(DRIVER_EXCEPTION, message);
    }

    private BusinessRuleException getException(String message, String value) {
        return new BusinessRuleException(DRIVER_EXCEPTION, message, value);
    }

    private void validateCnh(String cnh) {
        Optional.of(ServiceUtils.isCnh(cnh)).orElseThrow(() -> getException("cnh.not.valid"));
    }

    private void validateCpf(String cpf) {
        Optional.of(ServiceUtils.isCpf(cpf)).orElseThrow(() -> getException("cpf.not.valid"));
    }

    private void validateDto(DriverDTO dto) {
        validateUnicity(dto);
        validateCpf(dto.getCpf());
        validateCnh(dto.getCnh());
        validateUniversity(dto.getUniversity());
    }

    private void validateUnicity(DriverDTO dto) {
        Specification<Driver> specs = buildSpecAnd(forEquals(Driver_.cpf, dto.getCpf()), forNotEquals(Driver_.id, dto.getId()));
        Optional.of(repository.count(specs) > 0).orElseThrow(() -> getException("driver.field.unique.male", "field.cpf"));
        specs = buildSpecAnd(forEquals(Driver_.registrationNumber, dto.getRegistrationNumber()), forNotEquals(Driver_.id, dto.getId()));
        Optional.of(repository.count(specs) > 0).orElseThrow(() -> getException("driver.field.unique.male", "field.registration.number"));
        specs = buildSpecAnd(forEquals(Driver_.email, dto.getEmail()), forNotEquals(Driver_.id, dto.getId()));
        Optional.of(repository.count(specs) > 0).orElseThrow(() -> getException("driver.field.unique.male", "field.email"));
        specs = buildSpecAnd(forEquals(Driver_.phoneNumber, dto.getPhoneNumber()), forNotEquals(Driver_.id, dto.getId()));
        Optional.of(repository.count(specs) > 0).orElseThrow(() -> getException("driver.field.unique.male", "field.phone.number"));
    }

    private void validateUniversity(BasicIdDTO university) {
        universityService.existsById(university.getId());
    }
}
