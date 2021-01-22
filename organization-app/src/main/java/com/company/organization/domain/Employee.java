package com.company.organization.domain;

import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Getter
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager")
    private Employee manager;

    @OneToMany
    private final List<Employee> managed = new ArrayList<>();

    public Employee() {
    }

    public Employee(final String name) {
        this.name = name;
    }

    public void setManager(final Employee parent) {
        this.manager = parent;
    }

    public Optional<Employee> getManager() {
        return Optional.ofNullable(manager);
    }

    public void addManaged(Employee managed) {
        this.managed.add(managed);
    }

    public boolean isRoot() {
        return manager == null;
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
