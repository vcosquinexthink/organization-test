package com.company.organization.domain;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Organization {

    private Map<String, String> storage = new HashMap<>();

    public Map<String, String> getFlatOrganization() {
        return storage;
    }

    public void addEmployee(final String employeeName, final String managedByName) {
        storage.put(employeeName, managedByName);
    }
}
