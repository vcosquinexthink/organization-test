package com.company.organization.rest;

import com.company.organization.domain.Employee;
import com.company.organization.domain.Organization;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Component
public class HierarchyRepresentation {

    public static final Object EMPTY = new Object();

    private final Organization organization;

    public HierarchyRepresentation(final Organization organization) {
        this.organization = organization;
    }

    public Map<String, List<Object>> getHierarchy() {
        if (organization.hasRootEmployee()) {
            final var rootEmployee = employeeToMap(organization.getRootEmployee());
            return (rootEmployee);
        } else {
            return Map.of();
        }
    }

    private Map<String, List<Object>> employeeToMap(final Employee employee) {
        return Map.of(employee.getName(), childrenToList(employee.getManaged()));
    }

    private List<Object> childrenToList(final List<Employee> employees) {
        final List<Object> childrenToList = employees.stream().map(this::employeeToMap).collect(toList());
        childrenToList.sort(comparing(Object::toString));
        return childrenToList;
    }

    public Map<String, Object> getManagementChain(final Employee employee) {
        return employeeToManagerMap(employee);
    }

    private Map<String, Object> employeeToManagerMap(final Employee employee) {
        final var manager = employee.getManager();
        if (manager != null) {
            return Map.of(employee.getName(), employeeToManagerMap(manager));
        } else {
            return Map.of(employee.getName(), EMPTY);
        }
    }
}
