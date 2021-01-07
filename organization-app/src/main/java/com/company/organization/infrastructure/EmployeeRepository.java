package com.company.organization.infrastructure;

import com.company.organization.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByNameIs(String name);

    default Employee findByNameOrCreate(String name) {
        final var found = findByNameIs(name);
        return found != null ? found : new Employee(name);
    }

    List<Employee> findDistinctByManagerIsNull();

    default List<Employee> findRoots() {
        return findDistinctByManagerIsNull();
    }

    long countDistinctByManagerIsNull();

    default long countRoots() {
        return countDistinctByManagerIsNull();
    }

    List<Employee> findByManagerIs(Employee manager);
}