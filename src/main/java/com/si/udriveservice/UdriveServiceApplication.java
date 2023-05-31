package com.si.udriveservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.si.udriveservice"})
public class UdriveServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UdriveServiceApplication.class, args);
    }

}
