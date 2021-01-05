package com.company.organization.rest;

import com.company.organization.domain.Organization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class ApplicationController {

    private final Organization organization;

    private final HierarchyRepresentation hierarchyRepresentation;

    public ApplicationController(final Organization organization, final HierarchyRepresentation hierarchyRepresentation) {
        this.organization = organization;
        this.hierarchyRepresentation = hierarchyRepresentation;
    }

    @PostMapping(value = "/organization", consumes = "application/json")
    public void setOrganization(@RequestBody final Map<String, String> organizationReceived) {
        log.info("received organization {}", organizationReceived.toString());
        organization.addEmployees(organizationReceived);
    }

    @GetMapping(value = "/organization", produces = "application/json")
    public String getOrganization() {
        return hierarchyRepresentation.toJson();
    }
}
