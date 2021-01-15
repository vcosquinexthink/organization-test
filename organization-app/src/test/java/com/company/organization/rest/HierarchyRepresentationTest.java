package com.company.organization.rest;

import com.company.organization.domain.Employee;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class HierarchyRepresentationTest {

    @Test
    @SneakyThrows
    public void itShouldReturnTheRightRepresentation() {
        final var jonas = new Employee("Jonas");
        final var sophie = new Employee("Sophie");
        jonas.addManaged(sophie);
        final var nick = new Employee("Nick");
        sophie.addManaged(nick);
        final var pete = new Employee("Pete");
        final var barbara = new Employee("Barbara");
        nick.addManaged(pete);
        nick.addManaged(barbara);

        final var expected =
            Map.of("Jonas", Map.of("Sophie", Map.of("Nick", Map.of("Barbara", Map.of(), "Pete", Map.of()))));
        assertThat(HierarchyRepresentation.downstreamHierarchy(List.of(jonas)), is(expected));
    }

    @Test
    public void itShouldReturnTheRightRepresentationWhenNoRoot() {

        assertThat(HierarchyRepresentation.downstreamHierarchy(List.of()), is(Map.of()));
    }

    @Test
    @SneakyThrows
    public void itShouldReturnTheRightManagementChain() {
        final var jonas = new Employee("1");
        final var sophie = new Employee("2");
        jonas.addManaged(sophie);
        sophie.setManager(jonas);
        final var nick = new Employee("3");
        sophie.addManaged(nick);
        nick.setManager(sophie);

        final var expectedChain =
            Map.of("3", Map.of("2", Map.of("1", Map.of())));
        assertThat(HierarchyRepresentation.upstreamHierarchy(nick), is(expectedChain));
    }
}
