package com.company.organization.rest;

import com.company.organization.domain.Organization;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationControllerTest {

    @InjectMocks
    ApplicationController applicationController;

    @Mock
    Organization organizationMock;

    @Test
    @SneakyThrows
    public void getFlatOrganizationShouldCallOrganization() {
        final var organizationMap = Map.of("minion", "boss",
            "boss", "superboss");
        when(organizationMock.getFlatOrganization()).thenReturn(organizationMap);

        final var flatOrganization = applicationController.getFlatOrganization();

        assertThat(flatOrganization, is(organizationMap));
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

        verify(organizationMock).addEmployee("boss", "superboss");
        verify(organizationMock).addEmployee("minion1", "boss");
        verify(organizationMock).addEmployee("minion2", "boss");
    }

}