package com.company.organization.rest;

import com.company.organization.domain.Employee;
import com.company.organization.domain.Organization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Component
@Slf4j
public class HierarchyRepresentation {

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
}
