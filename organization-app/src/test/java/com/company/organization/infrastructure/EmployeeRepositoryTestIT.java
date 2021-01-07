package com.company.organization.infrastructure;

import com.company.organization.domain.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
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

        final var employee1 = employeeRepository.findByNameIs("employee 1");
        assertThat(employee1, is(employee));
        assertThat(employee1.getManager(), is(manager));
        assertThat(employee1.getManager().getManager(), is(boss));

        final var allEmployees = employeeRepository.findAll();
        assertThat(allEmployees.size(), is(3));
    }

    @Test
    public void findRootsShouldRetrieveAllRoots() {
        final var boss1 = new Employee("boss 1");
        final var employee1 = new Employee("employee 1");
        employee1.addManager(boss1);
        employeeRepository.save(employee1);
        final var boss2 = new Employee("boss 2");
        final var employee2 = new Employee("employee 2");
        employee2.addManager(boss2);
        employeeRepository.save(employee2);

        final var roots = employeeRepository.findRoots();
        assertThat(roots.size(), is(2));
        assertThat(roots, contains(boss1, boss2));
    }

    @Test
    public void countRootsShouldCountAllRoots() {
        final var boss1 = new Employee("boss 1");
        final var employee1 = new Employee("employee 1");
        employee1.addManager(boss1);
        employeeRepository.save(employee1);
        final var boss2 = new Employee("boss 2");
        final var employee2 = new Employee("employee 2");
        employee2.addManager(boss2);
        employeeRepository.save(employee2);

        final var rootsNumber = employeeRepository.countRoots();
        assertThat(rootsNumber, is((long) 2));
    }

    @Test
    public void findByNameOrCreateShouldCreateWhenEmployeeDoentsExist() {
        final var newEmployeeCreated = employeeRepository.findByNameOrCreate("new employee");

        assertThat(new Employee("new employee"), is(newEmployeeCreated));
    }

    @Test
    public void findByManagerIsShouldReturnManagedEmployees() {
        final var boss = new Employee("boss 1");

        final var employee1 = new Employee("employee 1");
        employee1.addManager(boss);
        employeeRepository.save(employee1);

        final var managedEmployees = employeeRepository.findByManagerIs(boss);
        assertThat(managedEmployees, contains(employee1));
    }
}
