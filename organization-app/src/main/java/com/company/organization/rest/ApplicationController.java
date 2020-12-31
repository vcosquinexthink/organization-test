package com.company.organization.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class ApplicationController {

    static Map<String, String> organization;

    @GetMapping(value = "/organization", produces = "application/json")
    public String getOrganization() throws Exception { // todo: exception
        return new ObjectMapper().writeValueAsString(organization);
    }

    @PostMapping(value = "/organization", consumes = "application/json")
    public void setOrganization(@RequestBody final Map<String, String> organization) {
        log.info("received organization {}", organization.toString());
        ApplicationController.organization = organization;
    }
}
