package com.si.udriveservice.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class BasicIdDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    @NotNull
    private Long id;
}