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
        final var Jane = new Employee("Jane");
        final var Olga = new Employee("Olga");
        Jane.addManaged(Olga);
        final var Amanda = new Employee("Amanda");
        Olga.addManaged(Amanda);
        final var Carl = new Employee("Carl");
        final var Samuel = new Employee("Samuel");
        Amanda.addManaged(Carl);
        Amanda.addManaged(Samuel);

        final var expected =
            Map.of("Jane", Map.of("Olga", Map.of("Amanda", Map.of("Samuel", Map.of(), "Carl", Map.of()))));
        assertThat(HierarchyRepresentation.downstreamHierarchy(List.of(Jane)), is(expected));
    }

    @Test
    public void itShouldReturnTheRightRepresentationWhenNoRoot() {

        assertThat(HierarchyRepresentation.downstreamHierarchy(List.of()), is(Map.of()));
    }

    @Test
    @SneakyThrows
    public void itShouldReturnTheRightManagementChain() {
        final var Jane = new Employee("1");
        final var Olga = new Employee("2");
        Jane.addManaged(Olga);
        Olga.setManager(Jane);
        final var Amanda = new Employee("3");
        Olga.addManaged(Amanda);
        Amanda.setManager(Olga);

        final var expectedChain =
            Map.of("3", Map.of("2", Map.of("1", Map.of())));
        assertThat(HierarchyRepresentation.upstreamHierarchy(Amanda), is(expectedChain));
    }
}
