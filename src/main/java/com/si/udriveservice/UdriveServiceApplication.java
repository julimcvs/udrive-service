package com.si.udriveservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.si.udriveservice"})
public class UdriveServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UdriveServiceApplication.class, args);
    }

}
