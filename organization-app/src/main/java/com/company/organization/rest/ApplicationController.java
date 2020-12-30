package com.company.organization.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @GetMapping("/organization")
    public String getOrganization() {
        return "hello";
    }
}
