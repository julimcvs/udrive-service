package com.si.udriveservice.model.entity;

import com.si.udriveservice.model.enums.TokenTypeEnum;
import com.si.udriveservice.model.enums.YesNoEnum;
import jakarta.persistence.CascadeType;
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
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "tb_token")
public class Token implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String SEQ_TOKEN = "seq_token";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_TOKEN)
    @SequenceGenerator(name = SEQ_TOKEN, sequenceName = SEQ_TOKEN, allocationSize = 1)
    private Long id;

    @Column(name = "token", length = 6)
    private String tokenCode;

    @Column(name = "creation_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "used")
    @Enumerated(EnumType.STRING)
    private YesNoEnum used;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TokenTypeEnum type;

    @JoinColumn(name = "driver_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    private Driver driver;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    private User user;
}

