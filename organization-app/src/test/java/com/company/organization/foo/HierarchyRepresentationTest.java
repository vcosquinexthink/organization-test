package com.company.organization.foo;

import com.company.organization.domain.Hierarchy;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class HierarchyRepresentationTest {

    @SneakyThrows
    @Test
    public void itShouldReturnTheRightRepresentation() {

        final var hierarchy = new Hierarchy();
        hierarchy.addEmployee("low-1", "mid");
        hierarchy.addEmployee("low-2", "mid");
        hierarchy.addEmployee("mid", "high");
        final var json = new HierarchyRepresentation(hierarchy).json();

        assertThat(json, is("{\"high\":[{\"mid\":[{\"low-1\":[]},{\"low-2\":[]}]}]}"));
    }

    @SneakyThrows
    @Test
    public void itShouldReturnTheRightRepresentationSample2() {

        final var hierarchy = new Hierarchy();
        hierarchy.addEmployee("Pete", "Nick");
        hierarchy.addEmployee("Barbara", "Nick");
        hierarchy.addEmployee("Nick", "Sophie");
        hierarchy.addEmployee("Sophie", "Jonas");

        final var json = new HierarchyRepresentation(hierarchy).json();

        assertThat(json, is("{\"Jonas\":[{\"Sophie\":[{\"Nick\":[{\"Pete\":[]},{\"Barbara\":[]}]}]}]}"));
    }
}