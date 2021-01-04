package com.company.organization.domain;

import java.util.HashSet;
import java.util.Set;

class Hierarchy {

    Set<Employee> employees = new HashSet<>();

    public void feed(final String employee, final String manager) {
        final var parent = new Employee(manager);
        final var child = new Employee(employee);
        child.addParent(parent);
        employees.add(child);
        employees.add(parent);
    }

    public int staffSize() {
        return employees.size();
    }
}
