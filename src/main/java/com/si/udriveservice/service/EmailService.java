package com.si.udriveservice.service;

import com.si.udriveservice.configuration.BusinessRuleException;
import com.si.udriveservice.integration.EmailClient;
import com.si.udriveservice.model.record.EmailContent;
import com.si.udriveservice.model.record.EmailDTO;
import com.si.udriveservice.model.record.ResponseEmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {
    public static final String EMAIL_EXCEPTION = "email.exception";
    private final EmailClient emailClient;

    public void sendValidationEmail(String name, String token, String email) {
        // TODO colocar URL do Front-End
        String tokenUrl = String.format("%s/%s", "https://www.google.com/", token);
        EmailContent mailContent = new EmailContent(name, tokenUrl);
        EmailDTO dto = new EmailDTO(mailContent, email);
        ResponseEmailDTO response = emailClient.sendValidationEmail(dto);
        Optional.of(response.accepted().contains(email)
                        && response.response().contains("250")
                        && response.response().contains("OK"))
                .orElseThrow(() -> getException());
    }

    private BusinessRuleException getException() {
        return new BusinessRuleException(EMAIL_EXCEPTION, "error.sending.email");
    }
}
