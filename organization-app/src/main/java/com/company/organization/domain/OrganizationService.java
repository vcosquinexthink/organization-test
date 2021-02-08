package com.company.organization.domain;


import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Component
public class OrganizationService {

    private final Organization organization;

    public OrganizationService(final Organization organization) {
        this.organization = organization;
    }

    public Optional<Employee> getRoot() {
        return organization.getRoot();
    }

    public Optional<Employee> getEmployee(final String employeeName) {
        return organization.getEmployee(employeeName);
    }

    @Transactional
    public void addEmployees(final Map<String, String> newEmployees) {
        newEmployees.forEach(organization::addEmployee);
        organization.verifySingleRoot();
    }
}
