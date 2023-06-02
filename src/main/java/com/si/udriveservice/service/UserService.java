package com.si.udriveservice.service;

import com.si.udriveservice.configuration.BusinessRuleException;
import com.si.udriveservice.configuration.security.JwtService;
import com.si.udriveservice.model.dto.BasicIdDTO;
import com.si.udriveservice.model.dto.PasswordDTO;
import com.si.udriveservice.model.dto.UserDTO;
import com.si.udriveservice.model.entity.Role;
import com.si.udriveservice.model.entity.User;
import com.si.udriveservice.model.entity.User_;
import com.si.udriveservice.model.enums.StatusEnum;
import com.si.udriveservice.model.enums.TokenTypeEnum;
import com.si.udriveservice.model.mapper.UserMapper;
import com.si.udriveservice.repository.BaseSpecs;
import com.si.udriveservice.repository.UserRepository;
import com.si.udriveservice.service.utils.ServiceUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Primary
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class UserService implements BaseSpecs<User>, UserDetailsService {
    public static final Integer PASSWORD_LENGHT = 6;
    public static final String USER_EXCEPTION = "user.exception";
    public static final String ENTITY_NOT_FOUND = "entity.not.found.male";
    public static final String USER = "entity.user";
    public static final String UNIVERSITY = "entity.university";
    private final UserRepository repository;
    private final UserMapper mapper;
    private final UniversityService universityService;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public void deleteById(Long id) {
        User entity = findEntityById(id);
        entity.setStatus(StatusEnum.INACTIVE);
        repository.save(entity);
    }

    public UserDTO findById(Long id) {
        return mapper.toDto(findEntityById(id));
    }

    public User findEntityById(Long id) {
        return repository.findByIdAndStatus(id, StatusEnum.ACTIVE)
                .orElseThrow(() -> getException(ENTITY_NOT_FOUND, USER));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmailAndStatus(username, StatusEnum.ACTIVE)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public UserDTO save(UserDTO dto) {
        validateDto(dto);
        User entity = mapper.toEntity(dto);
        User savedEntity = findSavedEntity(dto.getId());
        if (savedEntity != null) {
            return updateEntity(dto, entity, savedEntity);
        }
        entity.setStatus(StatusEnum.ACTIVE);
        entity.setRole(Role.USER);
        String password =  RandomStringUtils.random(PASSWORD_LENGHT, true, true);
        entity.setPassword(passwordEncoder.encode(password));
        entity = repository.save(entity);
        emailService.sendValidationEmail(entity.getFullName(), password, entity.getEmail());
        var jwtToken = jwtService.generateToken(entity);
        UserDTO returnDto = mapper.toDto(entity);
        returnDto.setJwt(jwtToken);
        return returnDto;
    }

    @Transactional
    public UserDTO savePassword(PasswordDTO dto) {
        User entity = repository.findByEmailAndStatus(dto.getEmail(), StatusEnum.ACTIVE)
                .orElseThrow(() -> getException(ENTITY_NOT_FOUND, USER));
        validatePasswordDto(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        tokenService.validateToken(dto.getToken(), entity.getEmail(), TokenTypeEnum.USER);
        var jwt = jwtService.generateToken(entity);
        UserDTO userDTO = mapper.toDto(repository.save(entity));
        userDTO.setJwt(jwt);
        return userDTO;
    }

    private User findSavedEntity(Long id) {
        if (id == null) {
            return null;
        }
        return findEntityById(id);
    }

    private BusinessRuleException getException(String message) {
        return new BusinessRuleException(USER_EXCEPTION, message);
    }

    private BusinessRuleException getException(String message, String value) {
        return new BusinessRuleException(USER_EXCEPTION, message, value);
    }

    private UserDTO updateEntity(UserDTO dto, User entity, User savedEntity) {
        entity.setCpf(savedEntity.getCpf());
        entity.setStatus(savedEntity.getStatus());
        entity.setPassword(savedEntity.getPassword());
        entity.setRole(savedEntity.getRole());
        if (Objects.equals(savedEntity.getUniversity().getId(),
                dto.getUniversity().getId())) {
            entity.setRegistrationNumber(savedEntity.getRegistrationNumber());
        }
        return mapper.toDto(repository.save(entity));
    }

    private void validateCpf(String cpf) {
        Optional.of(ServiceUtils.isCpf(cpf)).orElseThrow(() -> getException("cpf.not.valid"));
    }

    private void validateDto(UserDTO dto) {
        validateUnicity(dto);
        validateCpf(dto.getCpf());
        validateUniversity(dto.getUniversity());
    }

    private void validatePasswordDto(PasswordDTO dto) {
        if (!StringUtils.equals(dto.getPassword(), dto.getConfirmPassword())) {
            throw getException("passwords.not.match");
        }
    }

    private void validateUnicity(UserDTO dto) {
        Specification<User> specs = buildSpecAnd(forEquals(User_.cpf, dto.getCpf()), forNotEquals(User_.id, dto.getId()));
        if (repository.count(specs) != 0) {
            throw getException("user.field.unique.male", "field.cpf");
        }
        specs = buildSpecAnd(forEquals(User_.registrationNumber, dto.getRegistrationNumber()), forNotEquals(User_.id, dto.getId()));
        if (repository.count(specs) != 0) {
            throw getException("user.field.unique.male", "field.registration.number");
        }
        specs = buildSpecAnd(forEquals(User_.email, dto.getEmail()), forNotEquals(User_.id, dto.getId()));
        if (repository.count(specs) != 0) {
            throw getException("user.field.unique.male", "field.email");
        }
        specs = buildSpecAnd(forEquals(User_.phoneNumber, dto.getPhoneNumber()), forNotEquals(User_.id, dto.getId()));
        if (repository.count(specs) != 0) {
            throw getException("user.field.unique.male", "field.phone.number");
        }
    }

    private void validateUniversity(BasicIdDTO university) {
        if (!universityService.existsById(university.getId())) {
            throw getException(ENTITY_NOT_FOUND, UNIVERSITY);
        }
    }
}
