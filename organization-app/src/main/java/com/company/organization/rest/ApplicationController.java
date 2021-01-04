package com.company.organization.rest;

import com.company.organization.domain.Organization;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class ApplicationController {

    private Organization organization;

    public ApplicationController(final Organization organization) {
        this.organization = organization;
    }

    @PostMapping(value = "/organization", consumes = "application/json")
    public void setOrganization(@RequestBody final Map<String, String> organizationReceived) {
        log.info("received organization {}", organizationReceived.toString());
        organizationReceived.forEach((employee, managedBy) -> organization.addEmployee(employee, managedBy));
    }

    @GetMapping(value = "/organization", produces = "application/json")
    public Map<String, String> getFlatOrganization() throws JsonProcessingException {
        return organization.getFlatOrganization();
    }

}
