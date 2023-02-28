package com.si.udriveservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.MessageSource;

import java.io.Serializable;
import java.util.Locale;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String message;
    private String code;

    public ApiErrorDTO(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public ApiErrorDTO(String title, String message, String[] values, MessageSource ms, Locale locale) {
        this.title = ms.getMessage(title, null, locale);
        joinValues(values);
        this.code = Optional.ofNullable(ms.getMessage(this.code, null, this.code, locale)).orElse("");
        this.message = ms.getMessage(message, this.code.split(","), locale);
    }

    private void joinValues(String[] values) {
        if (values != null) {
            this.code = String.join(",", values);
            return;
        }
        this.code = "";
    }
}