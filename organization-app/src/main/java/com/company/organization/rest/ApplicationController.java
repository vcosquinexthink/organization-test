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

//    public void setOrganizationTree(@RequestBody final Map<String, String> organizationMap) {
//        log.info("received organizationMap {}", organizationMap.toString());
//        final var allEmployees = new ArrayList<String>(new HashSet<>(organizationMap.values()));
//        final var managedEmployees = organizationMap.keySet();
//        allEmployees.removeAll(managedEmployees);
//        organization.addEmployee(allEmployees.get(0));
//        organizationMap.forEach((employee, manager) -> organization.addEmployee(employee, manager));
//    }
}
