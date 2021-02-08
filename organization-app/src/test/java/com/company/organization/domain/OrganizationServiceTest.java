package com.company.organization.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceTest {

    @Mock
    Organization organizationMock;

    @InjectMocks
    OrganizationService organizationService;

    @Test
    public void getRootShouldCallOrganization() {
        final var root = Optional.of(new Employee());
        when(organizationMock.getRoot()).thenReturn(root);

        final var found = organizationService.getRoot();

        assertThat(root, is(found));
    }

    @Test
    public void getEmployeeShouldCallOrganization() {
        final var employee = Optional.of(new Employee());
        when(organizationMock.getEmployee("name")).thenReturn(employee);

        final var found = organizationService.getEmployee("name");

        assertThat(employee, is(found));
    }

    @Test
    public void addEmployeesShouldCallOrganizationAndVerify() {
        organizationService.addEmployees(Map.of("a", "b", "c", "d"));

        verify(organizationMock).addEmployee("a", "b");
        verify(organizationMock).addEmployee("c", "d");
        verify(organizationMock).verifySingleRoot();
    }
}