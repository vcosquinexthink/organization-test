package com.company.organization.rest;

import com.company.organization.domain.Employee;
import com.company.organization.domain.Organization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper objectMapper;

    public HierarchyRepresentation(final Organization organization, final ObjectMapper objectMapper) {
        this.organization = organization;
        this.objectMapper = objectMapper;
    }

    public String toJson() {
        try {
            if (organization.hasRootEmployee()) {
                final var rootEmployee = employeeToMap(organization.getRootEmployee());
                return objectMapper.writeValueAsString(rootEmployee);
            } else {
                return objectMapper.writeValueAsString(new Object());
            }
        } catch (JsonProcessingException e) {
            log.error("unexpected error while writing hierarchy to json", e);
            throw new RuntimeException("unexpected error while writing hierarchy to json");
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
