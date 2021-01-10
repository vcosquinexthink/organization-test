package com.company.organization.domain;

import com.company.organization.infrastructure.EmployeeRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Component
public class Organization {

    private EmployeeRepository employeeRepository;

    public Organization(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getRootEmployee() {
        final var roots = employeeRepository.findRoots();
        if (roots.size() > 0) {
            return roots.get(0);
        } else {
            throw new NoSuchElementException("No root was found in the organization");
        }
    }

    public boolean hasRootEmployee() {
        return employeeRepository.countRoots() > 0;
    }

    public Employee getEmployee(final Employee employee) {
        return employeeRepository.findByNameIs(employee.getName()).orElseThrow(() -> new NoSuchElementException());
    }

    @Transactional
    public void addEmployees(final Map<String, String> newEmployees) throws DuplicateRootException, CyclicDependencyException {
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
            throw new DuplicateRootException("Error: More than one root was added: " + roots);
        }
    }

    private void checkCyclicDep(final Employee employee, final Employee self) {
        if (!employee.isRoot()) {
            final var manager = employee.getManager();
            if (manager != null) {
                if (manager.equals(self)) {
                    throw new CyclicDependencyException(format("Error: There is a cyclic dependency in employee [%s]", employee.getName()));
                }
                checkCyclicDep(manager, employee);
            }
        }
    }
}
