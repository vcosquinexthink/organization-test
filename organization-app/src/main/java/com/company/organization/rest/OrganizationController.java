package com.company.organization.rest;

import com.company.organization.domain.Employee;
import com.company.organization.domain.Organization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrganizationController {

    private final Organization organization;

    private final HierarchyRepresentation hierarchyRepresentation;

    public OrganizationController(final Organization organization, final HierarchyRepresentation hierarchyRepresentation) {
        this.organization = organization;
        this.hierarchyRepresentation = hierarchyRepresentation;
    }

    @PostMapping(value = "/organization", consumes = "application/json")
    public void setOrganization(@RequestBody final Map<String, String> organizationReceived) {
        organization.addEmployees(organizationReceived);
    }

    @GetMapping(value = "/organization", produces = "application/json")
    public String getOrganization() {
        return hierarchyRepresentation.toJson();
    }

    @GetMapping(value = "/organization/employee/{employeeName}", produces = "application/json")
    public Employee getEmployee(@PathVariable(value = "employeeName") String employeeName) {
        return organization.getEmployee(new Employee(employeeName));
    }
}
