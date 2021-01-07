package com.company.organization.infrastructure;

import com.company.organization.domain.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class EmployeeRepositoryTestIT {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void itShouldPersistOnSave() {
        final var boss = new Employee("boss 1");
        final var manager = new Employee("manager 1");
        final var employee = new Employee("employee 1");
        employee.addManager(manager);
        manager.addManager(boss);
        employeeRepository.save(employee);

        final var employee1 = employeeRepository.findByName("employee 1");
        assertThat(employee1, is(employee));
        assertThat(employee1.getManager(), is(manager));
        assertThat(employee1.getManager().getManager(), is(boss));

        final var allEmployees = employeeRepository.findAll();
        assertThat(allEmployees.size(), is(3));
    }
}
