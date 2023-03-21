package com.si.udriveservice.model.entity;

import com.si.udriveservice.model.enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "TB_UNIVERSITY")
public class University implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String SEQ_UNIVERSITY = "seq_university";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_UNIVERSITY)
    @SequenceGenerator(name = SEQ_UNIVERSITY, sequenceName = SEQ_UNIVERSITY, allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status;
}
