package com.company.organization.domain;

import com.company.organization.infrastructure.EmployeeRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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

    public boolean hasSeveralRootEmployees() {
        return employeeRepository.countRoots() > 1;
    }

    public boolean hasRootEmployee() {
        return employeeRepository.countRoots() > 0;
    }

    public Employee getEmployee(final Employee employee) {
        return employeeRepository.findByNameIs(employee.getName()).orElseThrow(() -> new NoSuchElementException());
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public void addEmployees(final Map<String, String> newEmployees) throws DuplicateRootException {
        newEmployees.forEach((employeeName, managerName) -> {
            final var employee = employeeRepository.findByNameOrCreate(employeeName);
            final var manager = employeeRepository.findByNameOrCreate(managerName);
            employee.setManager(manager);
            manager.addManaged(employee);
            employeeRepository.save(employee);
        });
        if (hasSeveralRootEmployees()) {
            final var roots = employeeRepository.findRoots().stream()
                .map(Employee::getName).collect(toList());
            throw new DuplicateRootException("Error: More than one root was added: " + roots);
        }
    }

}
