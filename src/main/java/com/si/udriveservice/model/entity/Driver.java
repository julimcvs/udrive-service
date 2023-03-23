package com.si.udriveservice.model.entity;

import com.si.udriveservice.model.enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "TB_DRIVER")
public class Driver implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String SEQ_DRIVER = "SEQ_DRIVER";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_DRIVER)
    @SequenceGenerator(name = SEQ_DRIVER, sequenceName = SEQ_DRIVER, allocationSize = 1)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "cnh", nullable = false)
    private String cnh;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false, referencedColumnName = "id")
    private University university;
}
