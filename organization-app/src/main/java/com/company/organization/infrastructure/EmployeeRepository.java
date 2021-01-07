package com.company.organization.infrastructure;

import com.company.organization.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e where e.name = ?1")
    Employee findByName(String name);

}