package com.company.organization.domain;

import com.company.organization.infrastructure.EmployeeRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizationTest {

    @InjectMocks
    Organization organization;

    @Mock
    EmployeeRepository employeeRepositoryMock;

    @SneakyThrows
    @Test
    public void hierarchyShouldGetManagedEmployee() {
        final var manager = new Employee("manager 1");
        final var employee = new Employee("employee 1");
        employee.setManager(manager);
        when(employeeRepositoryMock.findByNameIs("employee 1")).thenReturn(Optional.of(employee));

        final var foundEmployee = organization.getEmployee("employee 1").get().getManager();

        assertThat(foundEmployee.get(), is(new Employee("manager 1")));
    }

    @SneakyThrows
    @Test
    public void getRootEmployeeShouldReturnRootEmployee() {
        final var manager = new Employee("manager 1");
        when(employeeRepositoryMock.findRoots()).thenReturn(List.of(manager));

        assertThat(organization.getRoot().get(), is(new Employee("manager 1")));
    }

    @Test
    public void getRootEmployeeShouldFailIfNoRootPresent() {
        when(employeeRepositoryMock.findRoots()).thenReturn(List.of());
        organization.getRoot().isEmpty();
    }

    @Test
    public void getManagedEmployeeShouldFailIfNoSuchEmployeePresent() {
        when(employeeRepositoryMock.findByNameIs("foo")).thenReturn(Optional.empty());

        assertThat(organization.getEmployee("foo").isEmpty(), is(true));
    }

    @Test
    public void addEmployeesShouldFailWhenMoreThanOneRoot() {
        when(employeeRepositoryMock.countRoots()).thenReturn((long) 2);

        assertThrows(IllegalOrganizationException.class, () -> {
            organization.verifySingleRoot();
        });
    }

    @Test
    @SneakyThrows
    public void addEmployeeShouldCallRepositorySaveAndSetManager() {
        final var savedEmployee1Captor = ArgumentCaptor.forClass(Employee.class);
        final var employee1 = new Employee("employee1");
        final var boss = new Employee("b");
        when(employeeRepositoryMock.findByNameOrCreate("employee1")).thenReturn(employee1);
        when(employeeRepositoryMock.findByNameOrCreate("b")).thenReturn(boss);
        organization.addEmployee("employee1", "b");

        verify(employeeRepositoryMock).save(savedEmployee1Captor.capture());
        assertThat(savedEmployee1Captor.getValue().getManager().get(), is(boss));
    }
}