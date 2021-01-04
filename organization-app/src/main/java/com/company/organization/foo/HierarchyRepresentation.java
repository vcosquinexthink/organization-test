package com.company.organization.foo;

import com.company.organization.domain.Employee;
import com.company.organization.domain.Hierarchy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HierarchyRepresentation {

    Hierarchy hierarchy;

    public HierarchyRepresentation(final Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    public String json() throws JsonProcessingException {
        final var rootEmployee = hierarchy.getRootEmployee();
        return new ObjectMapper().writeValueAsString(employeeToJson(rootEmployee));
    }

    Map<String, List<Object>> employeeToJson(final Employee employee) {
        return Map.of(
            employee.getName(),
            childrenToList(hierarchy.getManagedEmployees(employee))
        );
    }

    List<Object> childrenToList(final List<Employee> employees) {
        return employees.stream().map( e -> employeeToJson(e)).collect(Collectors.toList());
    }
}
