package com.si.udriveservice.repository;

import com.si.udriveservice.model.entity.Token;
import com.si.udriveservice.model.enums.YesNoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenCodeAndUsed(String token, YesNoEnum used);
}
