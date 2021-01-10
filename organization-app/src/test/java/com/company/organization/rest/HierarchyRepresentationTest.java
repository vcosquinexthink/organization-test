package com.company.organization.rest;

import com.company.organization.domain.Employee;
import com.company.organization.domain.Organization;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HierarchyRepresentationTest {

    @InjectMocks
    private HierarchyRepresentation hierarchyRepresentation;

    @Mock
    Organization organizationMock;

    @Test
    @SneakyThrows
    public void itShouldReturnTheRightRepresentation() {
        when(organizationMock.hasRootEmployee()).thenReturn(true);
        final var jonas = new Employee("Jonas");
        when(organizationMock.getRootEmployee()).thenReturn(jonas);
        final var sophie = new Employee("Sophie");
        jonas.addManaged(sophie);
        final var nick = new Employee("Nick");
        sophie.addManaged(nick);
        final var pete = new Employee("Pete");
        final var barbara = new Employee("Barbara");
        nick.addManaged(pete);
        nick.addManaged(barbara);

        final var hierarchy = hierarchyRepresentation.getHierarchy();

        final var expected = Map.of("Jonas", List.of(Map.of("Sophie", List.of(Map.of("Nick", List.of(Map.of("Barbara", List.of()), Map.of("Pete", List.of())))))));
        assertThat(hierarchy, is(expected));
    }

    @Test
    public void itShouldReturnTheRightRepresentationWhenNoRoot() {
        when(organizationMock.hasRootEmployee()).thenReturn(false);

        final var hierarchy = hierarchyRepresentation.getHierarchy();

        assertThat(hierarchy, is(Map.of()));
    }
}