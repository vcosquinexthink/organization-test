package com.company.organization.rest;

import com.company.organization.domain.Employee;
import com.company.organization.domain.Organization;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HierarchyRepresentationTest {

    private HierarchyRepresentation hierarchyRepresentation;

    @Mock
    Organization organizationMock;

    @BeforeEach
    void setUp() {
        final var objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        hierarchyRepresentation = new HierarchyRepresentation(organizationMock, objectMapper);
    }

    @Test
    @SneakyThrows
    public void itShouldReturnTheRightRepresentation() {
        when(organizationMock.hasRootEmployee()).thenReturn(true);
        final var jonas = new Employee("Jonas");
        when(organizationMock.getRootEmployee()).thenReturn(jonas);
        final var sophie = new Employee("Sophie");
        when(organizationMock.getManagedEmployees(jonas)).thenReturn(List.of(sophie));
        final var nick = new Employee("Nick");
        when(organizationMock.getManagedEmployees(sophie)).thenReturn(List.of(nick));
        final var pete = new Employee("Pete");
        final var barbara = new Employee("Barbara");
        when(organizationMock.getManagedEmployees(nick)).thenReturn(List.of(pete, barbara));

        final var json = hierarchyRepresentation.toJson();

        assertThat(json, is("{\"Jonas\":[{\"Sophie\":[{\"Nick\":[{\"Barbara\":[]},{\"Pete\":[]}]}]}]}"));
    }

    @Test
    public void itShouldReturnTheRightRepresentationWhenNoRoot() {
        when(organizationMock.hasRootEmployee()).thenReturn(false);

        final var json = hierarchyRepresentation.toJson();

        assertThat(json, is("{}"));
    }
}