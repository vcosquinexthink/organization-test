package com.company.organization.rest;

import com.company.organization.domain.Employee;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class HierarchyRepresentation {

    public static Map<String, Map> downstreamHierarchy(final List<Employee> employees) {
        if (employees.isEmpty()) {
            return Map.of();
        } else {
            return employees.stream().collect(
                toMap(Employee::getName, employee -> downstreamHierarchy(employee.getManaged())));
        }
    }

    public static Map<String, Map> upstreamHierarchy(final Employee employee) {
        final var managerOpt = employee.getManager();
        if (managerOpt.isPresent()) {
            return Map.of(employee.getName(), upstreamHierarchy(managerOpt.get()));
        } else {
            return Map.of(employee.getName(), Map.of());
        }
    }
}
