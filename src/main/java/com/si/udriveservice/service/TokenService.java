package com.si.udriveservice.service;

import com.si.udriveservice.configuration.BusinessRuleException;
import com.si.udriveservice.model.entity.Driver;
import com.si.udriveservice.model.entity.Token;
import com.si.udriveservice.model.entity.User;
import com.si.udriveservice.model.enums.TokenTypeEnum;
import com.si.udriveservice.model.enums.YesNoEnum;
import com.si.udriveservice.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final Integer TOKEN_MAX_SIZE = 6;
    private static final String TOKEN_EXCEPTION = "token.exception";
    private final TokenRepository repository;

    public Token checkValidToken(String token) {
        Token entity = repository.findByTokenCodeAndUsed(token, YesNoEnum.NO).orElseThrow(
                () -> getTokenException("token.unavalable"));
        if (isTokenExpired(entity.getCreationDate())) {
            throw getTokenException("token.unavalable");
        }
        return entity;
    }

    public String generateTokenForDriver(Driver driver) {
        Token token = new Token();
        token.setTokenCode(RandomStringUtils.randomAlphanumeric(TOKEN_MAX_SIZE));
        token.setUsed(YesNoEnum.NO);
        token.setDriver(driver);
        token.setType(TokenTypeEnum.DRIVER);
        token = repository.save(token);
        return token.getTokenCode();
    }

    public String generateTokenForUser(User user) {
        Token token = new Token();
        token.setTokenCode(RandomStringUtils.randomAlphanumeric(TOKEN_MAX_SIZE));
        token.setUsed(YesNoEnum.NO);
        token.setUser(user);
        token.setType(TokenTypeEnum.USER);
        token = repository.save(token);
        return token.getTokenCode();
    }

    @Transactional
    public void validateToken(String token, String email, TokenTypeEnum type) {
        Token entity = checkValidToken(token);
        validateEmail(entity, email, type);
        entity.setUsed(YesNoEnum.YES);
        repository.save(entity);
    }

    private BusinessRuleException getTokenException(String message) {
        return new BusinessRuleException(TOKEN_EXCEPTION, message);
    }

    private boolean isTokenExpired(LocalDateTime creationDate) {
        return LocalDateTime.now().isAfter(creationDate.plusDays(1L));
    }

    private void validateEmail(Token entity, String email, TokenTypeEnum type) {
        switch (type) {
            case DRIVER -> {
                if (!StringUtils.equals(entity.getDriver().getEmail(), email)) {
                    throw getTokenException("token.email.not.match");
                }
            }
            case USER -> {
                if (!StringUtils.equals(entity.getUser().getEmail(), email)) {
                    throw getTokenException("token.email.not.match");
                }
            }
        }
    }
}
