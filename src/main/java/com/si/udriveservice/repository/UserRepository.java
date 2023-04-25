package com.si.udriveservice.repository;

import com.si.udriveservice.model.entity.User;
import com.si.udriveservice.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmailAndStatus(String email, StatusEnum status);

    Optional<User> findByIdAndStatus(Long id, StatusEnum status);
}
