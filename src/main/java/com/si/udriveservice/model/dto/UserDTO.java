package com.si.udriveservice.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank
    @Size(max = 100)
    private String fullName;

    @Valid
    @NotNull
    private BasicIdDTO university;

    @Min(0)
    @NotBlank
    @Digits(integer = 20, fraction = 0)
    private String registrationNumber;

    @NotBlank
    @Min(0)
    @Max(99999999999L)
    private String cpf;

    @NotBlank
    @Size(max = 100)
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;

    @Min(0)
    @NotBlank
    @Max(99999999999L)
    private String phoneNumber;

    private String jwt;
}