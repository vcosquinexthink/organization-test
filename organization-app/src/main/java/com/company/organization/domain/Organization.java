package com.company.organization.domain;

import com.company.organization.infrastructure.EmployeeRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Component
public class Organization {

    private EmployeeRepository employeeRepository;

    public Organization(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getRootEmployee() {
        return employeeRepository.findAll().stream().filter(Employee::isRoot).findFirst().orElseThrow();
    }

    public boolean hasSeveralRootEmployees() {
        return employeeRepository.findAll().stream().filter(Employee::isRoot).count() > 1;
    }

    public boolean hasRootEmployee() {
        return employeeRepository.findAll().stream().anyMatch(Employee::isRoot);
    }

    public Employee getEmployee(final Employee employee) {
        return employeeRepository.findAll().stream().filter(e -> e.equals(employee)).findFirst().orElseThrow();
    }

    public List<Employee> getManagedEmployees(final Employee employee) {
        return employeeRepository.findAll().stream()
            .filter(e -> e.getManager() != null && e.getManager().equals(employee)).collect(toList());
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public void addEmployees(final Map<String, String> newEmployees) throws DuplicateRootException {
        newEmployees.forEach(this::addEmployee);
        if (hasSeveralRootEmployees()) {
            final var roots = employeeRepository.findAll().stream().filter(Employee::isRoot)
                .map(Employee::getName).collect(toList());
            throw new DuplicateRootException("Error: More than one root was added: " + roots);
        }
    }

    private void addEmployee(final String employeeName, final String managerName) {
        final var employees = new HashSet<>(employeeRepository.findAll());
        final var manager = employees.stream().filter(e -> managerName.equals(e.getName()))
            .findFirst().orElse(new Employee(managerName));
        final var employee = employees.stream().filter(e -> employeeName.equals(e.getName()))
            .findFirst().orElse(new Employee(employeeName));
        employee.addManager(manager);
        employeeRepository.save(employee);
    }
}
