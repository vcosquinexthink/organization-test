package com.company.organization.domain;

import lombok.Data;

import java.util.Objects;

@Data
public class Employee {

    String name;
    Employee parent;

    public Employee(final String name) {
        this.name = name;
    }

    public void addParent(final Employee parent) {
        this.parent = parent;
    }

    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Employee employee = (Employee) o;
        return name.equals(employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
