package com.si.udriveservice.repository;

import com.si.udriveservice.model.entity.University;
import com.si.udriveservice.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    boolean existsByIdAndStatus(Long id, StatusEnum statusEnum);
}
