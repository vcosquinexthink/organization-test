package com.company.organization.domain;

import com.company.organization.infrastructure.EmployeeRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizationTest {

    @InjectMocks
    Organization organization;

    @Mock
    EmployeeRepository employeeRepositoryMock;

    @SneakyThrows
    @Test
    public void hierarchyShouldResolveManagedEmployeeOld() {
        organization.addEmployees(Map.of("Pete", "Nick",
            "Barbara", "Nick",
            "Nick", "Sophie",
            "Sophie", "Jonas"));
        assertThat(organization.getEmployee(
            new Employee("Pete")).getManager(), is(new Employee("Nick")));
    }

    @SneakyThrows
    @Test
    public void hierarchyShouldResolveManagedEmployee() {
        final var manager = new Employee("manager 1");
        final var employee = new Employee("employee 1");
        employee.addManager(manager);
        when(employeeRepositoryMock.findAll()).thenReturn(List.of(employee, manager));

        final var foundEmployee = organization.getEmployeeNew(
            new Employee("employee 1")).getManager();

        assertThat(foundEmployee, is(new Employee("manager 1")));
    }

    @SneakyThrows
    @Test
    public void hierarchyShouldHaveCorrectStaffSizeOld() {
        organization.addEmployees(Map.of("minion1", "boss",
            "minionc", "anothermoreboss",
            "miniond", "anothermoreboss",
            "miniona", "anotherboss",
            "minionb", "anotherboss",
            "anotherboss", "superboss",
            "anothermoreboss", "superboss",
            "boss", "superboss"));
        assertThat(organization.staffSize(), is(9));
    }

    @SneakyThrows
    @Test
    public void hierarchyShouldHaveCorrectStaffSize() {
        when(employeeRepositoryMock.count()).thenReturn((long) 9);

        final var actualStaffSize = organization.staffSizeNew();

        assertThat(actualStaffSize, is((long) 9));
    }

    @SneakyThrows
    @Test
    public void getRootEmployeeShouldReturnRootEmployeeOld() {
        organization.addEmployees(Map.of("Jonas", "Carla",
            "Pete", "Nick",
            "Barbara", "Nick",
            "Nick", "Sophie",
            "Sophie", "Jonas"));
        assertThat(organization.getRootEmployee(), is(new Employee("Carla")));
    }

    @SneakyThrows
    @Test
    public void getRootEmployeeShouldReturnRootEmployee() {
        final var manager = new Employee("manager 1");
        final var employee = new Employee("employee 1");
        employee.addManager(manager);
        when(employeeRepositoryMock.findAll()).thenReturn(List.of(employee, manager));

        assertThat(organization.getRootEmployeeNew(), is(new Employee("manager 1")));
    }

    @SneakyThrows
    @Test
    public void getEmployeesShouldReturnAllEmployeesOld() {
        organization.addEmployees(Map.of("Jonas", "Carla",
            "Pete", "Carla"));
        assertThat(organization.getEmployees().size(), is(3));
    }

    @SneakyThrows
    @Test
    public void getEmployeesShouldReturnAllEmployees() {
        final var manager = new Employee("manager 1");
        final var employee = new Employee("employee 1");
        employee.addManager(manager);
        when(employeeRepositoryMock.findAll()).thenReturn(List.of(employee, manager));

        assertThat(organization.getEmployeesNew(), contains(employee, manager));
    }

    @Test
    public void getRootEmployeeShouldFailIfNoRootPresentOld() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            organization.getRootEmployee();
        });
    }

    @Test
    public void getRootEmployeeShouldFailIfNoRootPresent() {
        when(employeeRepositoryMock.findAll()).thenReturn(List.of());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            organization.getRootEmployeeNew();
        });
    }

    @Test
    public void getManagedEmployeeShouldFailIfNoSuchEmployeePresentOld() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            organization.getEmployee(new Employee("foo"));
        });
    }

    @Test
    public void getManagedEmployeeShouldFailIfNoSuchEmployeePresent() {
        when(employeeRepositoryMock.findAll()).thenReturn(List.of());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            organization.getEmployeeNew(new Employee("foo"));
        });
    }

    @Test
    @SneakyThrows
    public void hasRootShouldReturnTrueWhenRootOld() {
        assertThat(organization.hasRootEmployee(), is(false));
        organization.addEmployees(Map.of("minion1", "boss"));
        assertThat(organization.hasRootEmployee(), is(true));
    }

    @Test
    @SneakyThrows
    public void hasRootShouldReturnTrueWhenRoot() {
        when(employeeRepositoryMock.findAll()).thenReturn(List.of());
        assertThat(organization.hasRootEmployeeNew(), is(false));
        when(employeeRepositoryMock.findAll()).thenReturn(List.of(new Employee("root")));
        assertThat(organization.hasRootEmployeeNew(), is(true));
    }

    @Test
    @SneakyThrows
    public void hasSeveralRootEmployeesShouldReturnTrueWhenSeveralRoots() {
        final var oneRoot = List.of(new Employee("root"));
        when(employeeRepositoryMock.findAll()).thenReturn(oneRoot);
        assertThat(organization.hasSeveralRootEmployeesNew(), is(false));
        final var twoRoots = List.of(new Employee("root"), new Employee("root2"));
        when(employeeRepositoryMock.findAll()).thenReturn(twoRoots);
        assertThat(organization.hasSeveralRootEmployeesNew(), is(true));
    }

    @Test
    public void hierarchyShouldFailWhenMoreThanOneRootOld() {
        Assertions.assertThrows(DuplicateRootException.class, () -> {
            organization.addEmployees(Map.of("Pete", "Nick",
                "Barbara", "Nick",
                "Nick", "Sophie",
                "Sophie", "Jonas",
                "Michael", "Barbara",
                "Ben", "Ana"));
        });
    }

    @Test
    public void hierarchyShouldFailWhenMoreThanOneRoot() {
        final var twoRoots = List.of(new Employee("root"), new Employee("root2"));
        when(employeeRepositoryMock.findAll()).thenReturn(twoRoots);

        Assertions.assertThrows(DuplicateRootException.class, () -> {
            organization.addEmployees2(Map.of("employee 1", "manager 1",
                "employee 2", "manager 2"));
        });
    }
}