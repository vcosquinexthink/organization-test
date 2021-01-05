package com.company.organization.rest;

import com.company.organization.domain.Employee;
import com.company.organization.domain.Organization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
                return objectMapper.writeValueAsString(employeeToJson(organization.getRootEmployee()));
            } else {
                return objectMapper.writeValueAsString(new Object());
            }
        } catch (JsonProcessingException e) {
            log.error("unexpected error while writing hierarchy to json", e);
            throw new RuntimeException("unexpected error while writing hierarchy to json");
        }
    }

    private Map<String, List<Object>> employeeToJson(final Employee employee) {
        return Map.of(employee.getName(), childrenToList(organization.getManagedEmployees(employee)));
    }

    private List<Object> childrenToList(final List<Employee> employees) {
        return employees.stream().map(this::employeeToJson).collect(toList());
    }
}
