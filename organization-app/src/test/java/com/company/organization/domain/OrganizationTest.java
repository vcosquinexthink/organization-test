package com.company.organization.domain;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class OrganizationTest {

    @SneakyThrows
    @Test
    public void hierarchyShouldResolveManagedEmployee() {
        final var hierarchy = new Organization();
        hierarchy.addEmployees(Map.of("Pete", "Nick",
            "Barbara", "Nick",
            "Nick", "Sophie",
            "Sophie", "Jonas"));
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Pete")).getManager(), is(new Employee("Nick")));
    }

    @SneakyThrows
    @Test
    public void hierarchyShouldHaveCorrectStaffSize() {
        final var hierarchy = new Organization();
        hierarchy.addEmployees(Map.of("minion1", "boss",
            "minionc", "anothermoreboss",
            "miniond", "anothermoreboss",
            "miniona", "anotherboss",
            "minionb", "anotherboss",
            "anotherboss", "superboss",
            "anothermoreboss", "superboss",
            "boss", "superboss"));
        assertThat(hierarchy.staffSize(), is(9));
    }

    @SneakyThrows
    @Test
    public void getRootEmployeeShouldReturnRootEmployee() {
        final var hierarchy = new Organization();
        hierarchy.addEmployees(Map.of("Jonas", "Carla",
            "Pete", "Nick",
            "Barbara", "Nick",
            "Nick", "Sophie",
            "Sophie", "Jonas"));
        assertThat(hierarchy.getRootEmployee(), is(new Employee("Carla")));
    }

    @SneakyThrows
    @Test
    public void getEmployeesShouldReturnAllEmployees() {
        final var hierarchy = new Organization();
        hierarchy.addEmployees(Map.of("Jonas", "Carla",
            "Pete", "Carla"));
        assertThat(hierarchy.getEmployees().size(), is(3));
    }

    @Test
    public void getRootEmployeeShouldFailIfNoRootPresent() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            new Organization().getRootEmployee();
        });
    }

    @Test
    public void getManagedEmployeeShouldFailIfNoSuchEmployeePresent() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            new Organization().getManagedEmployee(new Employee("foo"));
        });
    }

    @Test
    @SneakyThrows
    public void hasRootShouldReturnTrueWhenRoot() {
        final var hierarchy = new Organization();
        assertThat(hierarchy.hasRootEmployee(), is(false));
        hierarchy.addEmployees(Map.of("minion1", "boss"));
        assertThat(hierarchy.hasRootEmployee(), is(true));
    }

    @Test
    public void hierarchyShouldFailWhenMoreThanOneRoot() {
        Assertions.assertThrows(DuplicateRootException.class, () -> {
            final var hierarchy = new Organization();
            hierarchy.addEmployees(Map.of("Pete", "Nick",
                "Barbara", "Nick",
                "Nick", "Sophie",
                "Sophie", "Jonas",
                "Michael", "Barbara",
                "Ben", "Ana"));
        });
    }
}