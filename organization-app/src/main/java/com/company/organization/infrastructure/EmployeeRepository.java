package com.company.organization.infrastructure;

import com.company.organization.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByNameIs(String name);

    default Employee findByNameOrCreate(String name) {
        return findByNameIs(name).orElseGet(() -> new Employee(name));
    }

    List<Employee> findDistinctByManagerIsNull();

    default List<Employee> findRoots() {
        return findDistinctByManagerIsNull();
    }

    long countDistinctByManagerIsNull();

    default long countRoots() {
        return countDistinctByManagerIsNull();
    }

}