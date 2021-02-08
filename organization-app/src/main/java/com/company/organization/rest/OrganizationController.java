package com.company.organization.rest;

import com.company.organization.domain.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.company.organization.rest.HierarchyRepresentation.downstreamHierarchy;
import static com.company.organization.rest.HierarchyRepresentation.upstreamHierarchy;

@RestController
@Slf4j
public class OrganizationController {

    private final OrganizationService organization;

    public OrganizationController(final OrganizationService organization) {
        this.organization = organization;
    }

    @PostMapping(value = "/organization", consumes = "application/json")
    public void setOrganization(@RequestBody final Map<String, String> organizationReceived) {
        log.info("OrganizationController::setOrganization");
        organization.addEmployees(organizationReceived);
    }

    @GetMapping(value = "/organization", produces = "application/json")
    public Map<String, Map> getOrganization() {
        log.info("OrganizationController::getOrganization");
        return downstreamHierarchy(organization.getRoot().map(List::of).orElse(List.of()));
    }

    @GetMapping(value = "/organization/employee/{employeeName}/management", produces = "application/json")
    public Map<String, Map> getEmployee(@PathVariable(value = "employeeName") String employeeName) {
        log.info("OrganizationController::getEmployee");
        // todo.map(::upstreamHierarchy);
        return upstreamHierarchy(organization.getEmployee(employeeName).get());
    }
}
