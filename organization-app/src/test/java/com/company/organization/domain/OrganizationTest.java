package com.company.organization.domain;

import com.company.organization.infrastructure.EmployeeRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizationTest {

    @InjectMocks
    Organization organization;

    @Mock
    EmployeeRepository employeeRepositoryMock;

    @SneakyThrows
    @Test
    public void hierarchyShouldResolveManagedEmployee() {
        final var manager = new Employee("manager 1");
        final var employee = new Employee("employee 1");
        employee.addManager(manager);
        when(employeeRepositoryMock.findAll()).thenReturn(List.of(employee, manager));

        final var foundEmployee = organization.getEmployee(
            new Employee("employee 1")).getManager();

        assertThat(foundEmployee, is(new Employee("manager 1")));
    }

    @SneakyThrows
    @Test
    public void getRootEmployeeShouldReturnRootEmployee() {
        final var manager = new Employee("manager 1");
        final var employee = new Employee("employee 1");
        employee.addManager(manager);
        when(employeeRepositoryMock.findAll()).thenReturn(List.of(employee, manager));

        assertThat(organization.getRootEmployee(), is(new Employee("manager 1")));
    }

    @SneakyThrows
    @Test
    public void getEmployeesShouldReturnAllEmployees() {
        final var manager = new Employee("manager 1");
        final var employee = new Employee("employee 1");
        employee.addManager(manager);
        when(employeeRepositoryMock.findAll()).thenReturn(List.of(employee, manager));

        assertThat(organization.getEmployees(), contains(employee, manager));
    }

    @SneakyThrows
    @Test
    public void getManagedEmployeesNewShouldReturnEmployeeWithManager() {
        final var manager = new Employee("manager 1");
        final var employee1 = new Employee("employee1 1");
        employee1.addManager(manager);
        final var employee2 = new Employee("employee1 2");
        employee2.addManager(manager);
        when(employeeRepositoryMock.findAll()).thenReturn(List.of(employee1, employee2, manager));

        assertThat(organization.getManagedEmployees(new Employee("manager 1")), contains(employee1, employee2));
    }

    @Test
    public void getRootEmployeeShouldFailIfNoRootPresent() {
        when(employeeRepositoryMock.findAll()).thenReturn(List.of());
        assertThrows(NoSuchElementException.class, () -> {
            organization.getRootEmployee();
        });
    }

    @Test
    public void getManagedEmployeeShouldFailIfNoSuchEmployeePresent() {
        when(employeeRepositoryMock.findAll()).thenReturn(List.of());
        assertThrows(NoSuchElementException.class, () -> {
            organization.getEmployee(new Employee("foo"));
        });
    }

    @Test
    @SneakyThrows
    public void hasRootShouldReturnTrueWhenRoot() {
        when(employeeRepositoryMock.findAll()).thenReturn(List.of());
        assertThat(organization.hasRootEmployee(), is(false));
        when(employeeRepositoryMock.findAll()).thenReturn(List.of(new Employee("root")));
        assertThat(organization.hasRootEmployee(), is(true));
    }

    @Test
    @SneakyThrows
    public void hasSeveralRootEmployeesShouldReturnTrueWhenSeveralRoots() {
        final var oneRoot = List.of(new Employee("root"));
        when(employeeRepositoryMock.findAll()).thenReturn(oneRoot);
        assertThat(organization.hasSeveralRootEmployees(), is(false));
        final var twoRoots = List.of(new Employee("root"), new Employee("root2"));
        when(employeeRepositoryMock.findAll()).thenReturn(twoRoots);
        assertThat(organization.hasSeveralRootEmployees(), is(true));
    }

    @Test
    public void hierarchyShouldFailWhenMoreThanOneRoot() {
        final var twoRoots = List.of(new Employee("root"), new Employee("root2"));
        when(employeeRepositoryMock.findAll()).thenReturn(twoRoots);

        assertThrows(DuplicateRootException.class, () -> {
            organization.addEmployees(Map.of("employee 1", "manager 1",
                "employee 2", "manager 2"));
        });
    }
}