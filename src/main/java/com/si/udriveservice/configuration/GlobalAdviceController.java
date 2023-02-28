package com.si.udriveservice.configuration;


import com.si.udriveservice.model.dto.ApiErrorDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalAdviceController {
    private static final String GENERIC_EXCEPTION = "generic.exception";
    private static final String PARAMETERS_NOT_SATISFIED = "parameters.not.satisfied";
    private static final String INTERNAL_ERROR = "internal.error";
    private final MessageSource ms;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BusinessRuleException.class})
    public final ResponseEntity<ApiErrorDTO> businessRuleExceptionHandler(BusinessRuleException ex, Locale locale) {
        if (ex.getValues() != null) {
            Arrays.setAll(ex.getValues(), i -> ms.getMessage(ex.getValues()[i], null, locale));
        }
        return errorDefaultInternal(new ApiErrorDTO(ex.getStatus(), ex.getMessage(), ex.getValues(), ms, locale), HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorDTO beanValidationExceptionHandler(MethodArgumentNotValidException ex, Locale locale) {
        String[] error = new String[]{String.format("%s %s", ex.getFieldError().getField(), ex.getFieldError().getDefaultMessage())};
        return new ApiErrorDTO(GENERIC_EXCEPTION, PARAMETERS_NOT_SATISFIED, error, ms, locale);
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiErrorDTO exceptionHandler(Exception ex, Locale locale) {
        return new ApiErrorDTO(GENERIC_EXCEPTION, INTERNAL_ERROR, null, ms, locale);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiErrorDTO methodArgumentTypeMismatchExceptionHandler(Exception ex, Locale locale) {
        return new ApiErrorDTO(GENERIC_EXCEPTION, PARAMETERS_NOT_SATISFIED, null, ms, locale);
    }

    private ResponseEntity<ApiErrorDTO> errorDefaultInternal(ApiErrorDTO apiError, HttpStatus status) {
        return new ResponseEntity<>(apiError, status);
    }

}