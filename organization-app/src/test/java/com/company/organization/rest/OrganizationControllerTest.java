package com.company.organization.rest;

import com.company.organization.domain.Employee;
import com.company.organization.domain.Organization;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizationControllerTest {

    @InjectMocks
    OrganizationController organizationController;

    @Mock
    Organization organizationMock;

    @Test
    public void getOrganizationShouldGetHierarchyWithRoot() {
        when(organizationMock.getRoot()).thenReturn(Optional.of(new Employee("only employee")));

        assertThat(organizationController.getOrganization(), is(Map.of("only employee", Map.of())));
    }

    @Test
    public void getOrganizationShouldGetHierarchyWithEmptyOrganization() {
        when(organizationMock.getRoot()).thenReturn(Optional.empty());

        assertThat(organizationController.getOrganization(), is(Map.of()));
    }

    @Test
    @SneakyThrows
    public void setOrganizationShouldCallOrganizationForEachEmployee() {
        final Map<String, String> employees = Map.of(
            "minion1", "boss",
            "minion2", "boss",
            "boss", "superboss"
        );
        organizationController.setOrganization(employees);

        verify(organizationMock).addEmployees(employees);
    }

    @Test
    @SneakyThrows
    public void getEmployeeShouldReturnEmployee() {
        final var employee = new Employee("employee-1");
        when(organizationMock.getEmployee("employee-1")).thenReturn(Optional.of(employee));

        assertThat(organizationController.getEmployee("employee-1"), is(Map.of("employee-1", Map.of())));
    }
}