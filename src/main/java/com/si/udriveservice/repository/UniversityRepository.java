package com.si.udriveservice.repository;

import com.si.udriveservice.model.entity.University;
import com.si.udriveservice.model.enums.StatusEnum;
import com.si.udriveservice.model.record.UniversityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    boolean existsByIdAndStatus(Long id, StatusEnum status);

    List<University> findAllByStatus(StatusEnum status);
}
