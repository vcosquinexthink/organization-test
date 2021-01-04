package com.company.organization.domain;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Component
public class Hierarchy {

    Set<Employee> employees = new HashSet<>();

    public void addEmployee(final String employeeName, final String managerName) {
        final var parent = employees.stream().filter(e -> managerName.equals(e.getName()))
            .findFirst().orElse(new Employee(managerName));
        final var child = employees.stream().filter(e -> employeeName.equals(e.getName()))
            .findFirst().orElse(new Employee(employeeName));
        if (child.isRoot()) {
            employees.remove(child);
        }
        if (parent.isRoot()) {
            employees.remove(parent);
        }
        child.addParent(parent);
        employees.add(child);
        employees.add(parent);
    }

    public int staffSize() {
        return employees.size();
    }

    public Employee getRootEmployee() {
        return employees.stream().filter(e -> e.isRoot()).findFirst().orElseThrow();
    }

    public Employee getManagedEmployee(Employee employee) {
        return employees.stream().filter(e -> e.equals(employee)).findFirst().orElseThrow();
    }

    public List<Employee> getManagedEmployees(Employee employee) {
        return employees.stream()
            .filter(e -> e.getParent() != null && e.getParent().equals(employee)).collect(toList());
    }
}
