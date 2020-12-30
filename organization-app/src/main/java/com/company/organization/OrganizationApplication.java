package com.company.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class OrganizationApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(OrganizationApplication.class, args);
    }
}
