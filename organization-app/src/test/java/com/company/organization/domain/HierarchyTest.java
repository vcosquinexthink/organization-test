package com.company.organization.domain;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class HierarchyTest {

    @Test
    public void hierarchyShouldHaveCorrectStaffSizeSample1() {
        final var hierarchy = new Hierarchy();
        Map.of("Pete", "Nick",
            "Barbara", "Nick",
            "Nick", "Sophie",
            "Sophie", "Jonas")
            .forEach((employee, manager) -> hierarchy.feed(employee, manager));
        assertThat(hierarchy.staffSize(), is(5));
    }

    @Test
    public void hierarchyShouldHaveCorrectStaffSizeSample2() {
        final var hierarchy = new Hierarchy();
        Map.of("Pete", "Nick",
            "Sophie", "Jonas",
            "Barbara", "Nick",
            "Nick", "Sophie")
            .forEach((employee, manager) -> hierarchy.feed(employee, manager));
        assertThat(hierarchy.staffSize(), is(5));
    }

    @Test
    public void hierarchyShouldHaveCorrectStaffSizeSample3() {
        final var hierarchy = new Hierarchy();
        Map.of("Nick", "Sophie",
            "Pete", "Nick",
            "Sophie", "Jonas",
            "Barbara", "Nick")
            .forEach((employee, manager) -> hierarchy.feed(employee, manager));
        assertThat(hierarchy.staffSize(), is(5));
    }

    @Test
    public void hierarchyShouldHaveCorrectStaffSizeSample4() {
        final var hierarchy = new Hierarchy();
        Map.of("minion1", "boss",
            "minion2", "boss",
            "boss", "superboss")
            .forEach((employee, manager) -> hierarchy.feed(employee, manager));
        assertThat(hierarchy.staffSize(), is(4));
    }

    @Test
    public void hierarchyShouldHaveCorrectStaffSizeSample5() {
        final var hierarchy = new Hierarchy();
        Map.of("minion1", "boss",
            "minionc", "anothermoreboss",
            "miniond", "anothermoreboss",
            "miniona", "anotherboss",
            "minionb", "anotherboss",
            "anotherboss", "superboss",
            "anothermoreboss", "superboss",
            "boss", "superboss")
            .forEach((employee, manager) -> hierarchy.feed(employee, manager));
        assertThat(hierarchy.staffSize(), is(9));
    }

    @Test
    public void hierarchyShouldHaveCorrectStaffSizeWithTwoRoots() {
        final var hierarchy = new Hierarchy();
        Map.of("Pete", "Nick",
            "Barbara", "Nick",
            "Nick", "Sophie",
            "Sophie", "Jonas",
            "Annie", "Angela")
            .forEach((employee, manager) -> hierarchy.feed(employee, manager));
        assertThat(hierarchy.staffSize(), is(7));
    }
}