package com.si.udriveservice.integration;

import com.si.udriveservice.model.record.EmailDTO;
import com.si.udriveservice.model.record.ResponseEmailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "udrive-email", url = "${email-service.url}")
public interface EmailClient {
    @PostMapping("/mail-validation")
    ResponseEmailDTO sendValidationEmail(EmailDTO dto);
}
