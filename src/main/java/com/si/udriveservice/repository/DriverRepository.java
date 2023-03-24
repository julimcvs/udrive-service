package com.si.udriveservice.repository;

import com.si.udriveservice.model.entity.Driver;
import com.si.udriveservice.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {
    Optional<Driver> findByEmailAndStatus(String email, StatusEnum status);
    Optional<Driver> findByIdAndStatus(Long id, StatusEnum status);
}

