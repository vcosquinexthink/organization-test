package com.company.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.company.organization"})
public class OrganizationApplication {

    public static void main(final String[] args) {
        SpringApplication.run(OrganizationApplication.class, args);
    }
}
