package com.company.organization.domain;

import com.company.organization.infrastructure.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Component
public class Organization {

    private final Set<Employee> employees = new HashSet<>();
    private EmployeeRepository employeeRepository;

    @Deprecated
    public int staffSize() {
        return employees.size();
    }

    public long staffSizeNew() {
        return employeeRepository.count();
    }

    @Deprecated
    public Employee getRootEmployee() {
        return employees.stream().filter(Employee::isRoot).findFirst().orElseThrow();
    }

    public Employee getRootEmployeeNew() {
        return employeeRepository.findAll().stream().filter(Employee::isRoot).findFirst().orElseThrow();
    }

    @Deprecated
    public boolean hasSeveralRootEmployees() {
        return employees.stream().filter(Employee::isRoot).count() > 1;
    }

    public boolean hasSeveralRootEmployeesNew() {
        return employeeRepository.findAll().stream().filter(Employee::isRoot).count() > 1;
    }

    @Deprecated
    public boolean hasRootEmployee() {
        return employees.stream().anyMatch(Employee::isRoot);
    }

    public boolean hasRootEmployeeNew() {
        return employeeRepository.findAll().stream().anyMatch(Employee::isRoot);
    }

    @Deprecated
    public Employee getEmployee(final Employee employee) {
        return employees.stream().filter(e -> e.equals(employee)).findFirst().orElseThrow();
    }

    public Employee getEmployeeNew(final Employee employee) {
        return employeeRepository.findAll().stream().filter(e -> e.equals(employee)).findFirst().orElseThrow();
    }

    public List<Employee> getManagedEmployees(final Employee employee) {
        return employees.stream()
            .filter(e -> e.getManager() != null && e.getManager().equals(employee)).collect(toList());
    }

    @Deprecated
    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public List<Employee> getEmployeesNew() {
        return employeeRepository.findAll();
    }

    @Deprecated
    public void addEmployees(final Map<String, String> newEmployees) throws DuplicateRootException {
        newEmployees.forEach(this::addEmployee);
        if (hasSeveralRootEmployees()) {
            final var roots = employees.stream().filter(Employee::isRoot).map(Employee::getName).collect(toList());
            throw new DuplicateRootException("Error: More than one root was added: " + roots);
        }
    }

    @Deprecated
    private void addEmployee(final String employeeName, final String managerName) {
        final var parent = employees.stream().filter(e -> managerName.equals(e.getName()))
            .findFirst().orElse(new Employee(managerName));
        final var child = employees.stream().filter(e -> employeeName.equals(e.getName()))
            .findFirst().orElse(new Employee(employeeName));
        child.addManager(parent);
        if (child.isRoot()) {
            employees.remove(child);
        }
        if (parent.isRoot()) {
            employees.remove(parent);
        }
        employees.add(child);
        employees.add(parent);
    }

    public void addEmployees2(final Map<String, String> newEmployees) throws DuplicateRootException {
        newEmployees.forEach(this::addEmployee2);
        if (hasSeveralRootEmployeesNew()) {
            final var roots = employees.stream().filter(Employee::isRoot)
                .map(Employee::getName).collect(toList());
            throw new DuplicateRootException("Error: More than one root was added: " + roots);
        }
    }

    private void addEmployee2(final String employeeName, final String managerName) {
        final var employees_ = new HashSet<>(employeeRepository.findAll());
        final var manager = employees_.stream().filter(e -> managerName.equals(e.getName()))
            .findFirst().orElse(new Employee(managerName));
        final var employee = employees_.stream().filter(e -> employeeName.equals(e.getName()))
            .findFirst().orElse(new Employee(employeeName));
        employee.addManager(manager);
        if (employee.isRoot()) {
            employees_.remove(employee);
        }
        if (manager.isRoot()) {
            employees_.remove(manager);
        }
        employees_.add(employee);
        employees_.add(manager);
        employeeRepository.save(employee);
    }
}
