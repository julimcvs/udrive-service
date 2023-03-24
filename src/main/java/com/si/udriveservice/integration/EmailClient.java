package com.si.udriveservice.integration;

import com.si.udriveservice.model.record.EmailDTO;
import com.si.udriveservice.model.record.ResponseEmailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "udrive-email", url = "${email-service.url}")
public interface EmailClient {
    @RequestMapping(method = RequestMethod.POST, value = "/mail-validation")
    ResponseEmailDTO sendValidationEmail(EmailDTO dto);
}
