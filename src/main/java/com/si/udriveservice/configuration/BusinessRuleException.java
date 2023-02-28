package com.si.udriveservice.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessRuleException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String status;
    private final String message;
    private final String[] values;

    public BusinessRuleException(String status, String message) {
        super(message);
        this.status = status;
        this.message = message;
        this.values = new String[0];
    }


    public BusinessRuleException(String status, String message, String... values) {
        super(message);
        this.status = status;
        this.message = message;
        this.values = values;
    }
}