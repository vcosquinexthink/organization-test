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
        return managerOpt.<Map<String, Map>>map(value ->
                Map.of(employee.getName(), upstreamHierarchy(value)))
            .orElseGet(() ->
                Map.of(employee.getName(), Map.of()));
    }
}
