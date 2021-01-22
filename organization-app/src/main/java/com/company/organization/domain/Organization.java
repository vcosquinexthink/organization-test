package com.company.organization.domain;

import com.company.organization.infrastructure.EmployeeRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Component
public class Organization {

    private final EmployeeRepository employeeRepository;

    public Organization(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> getRoot() {
        return employeeRepository.findRoots().stream().findAny();
    }

    public Optional<Employee> getEmployee(final String employeeName) {
        return employeeRepository.findByNameIs(employeeName);
    }

    @Transactional
    public void addEmployees(final Map<String, String> newEmployees) throws IllegalOrganizationException {
        newEmployees.forEach((employeeName, managerName) -> {
            final var employee = employeeRepository.findByNameOrCreate(employeeName);
            final var manager = employeeRepository.findByNameOrCreate(managerName);
            employee.setManager(manager);
            manager.addManaged(employee);
            checkCyclicDep(employee, employee);
            employeeRepository.save(employee);
        });
        if (employeeRepository.countRoots() > 1) {
            final var roots = employeeRepository.findRoots().stream()
                .map(Employee::getName).collect(toList());
            throw new IllegalOrganizationException("Error: More than one root was added: " + roots);
        }
    }

    private void checkCyclicDep(final Employee employee, final Employee self) {
        if (!employee.isRoot()) {
            employee.getManager().ifPresent(manager -> {
                if (manager.equals(self)) {
                    throw new IllegalOrganizationException(format("Error: There is a cyclic dependency in employee [%s]", employee.getName()));
                }
                checkCyclicDep(manager, employee);
            });
        }
    }
}
