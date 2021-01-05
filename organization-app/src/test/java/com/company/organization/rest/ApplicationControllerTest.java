package com.company.organization.rest;

import com.company.organization.domain.DuplicateRootException;
import com.company.organization.domain.Organization;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationControllerTest {

    @InjectMocks
    ApplicationController applicationController;

    @Mock
    Organization organizationMock;

    @Mock
    HierarchyRepresentation hierarchyRepresentationMock;

    @Test
    public void getOrganizationShouldCallHierarchyRepresentation() {
        when(hierarchyRepresentationMock.toJson()).thenReturn("{}");

        final var hierarchyJson = applicationController.getOrganization();

        assertThat(hierarchyJson, is("{}"));
    }

    @Test
    @SneakyThrows
    public void setOrganizationShouldCallOrganizationForEachEmployee() {
        final Map<String, String> employees = Map.of(
            "minion1", "boss",
            "minion2", "boss",
            "boss", "superboss"
        );
        applicationController.setOrganization(employees);

        verify(organizationMock).addEmployees(employees);
    }

    @Test
    @SneakyThrows
    public void setOrganizationShouldThrowResponseStatusExceptionWhenDuplicateRoot() {
        doThrow(new DuplicateRootException("")).when(organizationMock).addEmployees(any());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            applicationController.setOrganization(Map.of());
        });
    }
}