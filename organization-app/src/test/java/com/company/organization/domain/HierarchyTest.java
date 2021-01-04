package com.company.organization.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

class HierarchyTest {

    @Test
    public void hierarchyShouldResolveManagedEmployee() {
        final var hierarchy = new Hierarchy();
        Map.of("Pete", "Nick",
            "Barbara", "Nick",
            "Nick", "Sophie",
            "Sophie", "Jonas")
            .forEach((employee, manager) -> hierarchy.addEmployee(employee, manager));
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Pete")).getParent(), is(new Employee("Nick")));
    }

    @Test
    public void hierarchyShouldHaveCorrectStaff() {

        final var hierarchy = new Hierarchy();
        hierarchy.addEmployee("Barbara", "Nick");
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Barbara")).getParent(), is(new Employee("Nick")));
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Nick")).getParent(), is(nullValue()));

        hierarchy.addEmployee("Sophie", "Jonas");
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Barbara")).getParent(), is(new Employee("Nick")));
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Sophie")).getParent(), is(new Employee("Jonas")));
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Jonas")).getParent(), is(nullValue()));

        hierarchy.addEmployee("Pete", "Nick");
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Barbara")).getParent(), is(new Employee("Nick")));
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Sophie")).getParent(), is(new Employee("Jonas")));
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Pete")).getParent(), is(new Employee("Nick")));
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Jonas")).getParent(), is(nullValue()));

        hierarchy.addEmployee("Nick", "Sophie");
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Barbara")).getParent(), is(new Employee("Nick")));
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Sophie")).getParent(), is(new Employee("Jonas")));
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Pete")).getParent(), is(new Employee("Nick")));
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Nick")).getParent(), is(new Employee("Sophie")));
        assertThat(hierarchy.getManagedEmployee(
            new Employee("Jonas")).getParent(), is(nullValue()));
    }

    @Test
    public void hierarchyShouldHaveCorrectStaffSize() {
        final var hierarchy = new Hierarchy();
        Map.of("minion1", "boss",
            "minionc", "anothermoreboss",
            "miniond", "anothermoreboss",
            "miniona", "anotherboss",
            "minionb", "anotherboss",
            "anotherboss", "superboss",
            "anothermoreboss", "superboss",
            "boss", "superboss")
            .forEach((employee, manager) -> hierarchy.addEmployee(employee, manager));
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
            .forEach((employee, manager) -> hierarchy.addEmployee(employee, manager));
        assertThat(hierarchy.staffSize(), is(7));
    }

    @Test
    public void getRootEmployeeShouldReturnRootEmployee() {
        final var hierarchy = new Hierarchy();
        Map.of("Jonas", "Carla",
            "Pete", "Nick",
            "Barbara", "Nick",
            "Nick", "Sophie",
            "Sophie", "Jonas")
            .forEach((employee, manager) -> hierarchy.addEmployee(employee, manager));
        assertThat(hierarchy.getRootEmployee(), is(new Employee("Carla")));
    }

    @Test
    public void getRootEmployeeShouldFailIfNoRootPresent() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            new Hierarchy().getRootEmployee();
        });
    }

    @Test
    public void getManagedEmployeeShouldFailIfNoSuchEmployeePresent() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            new Hierarchy().getManagedEmployee(new Employee("foo"));
        });
    }
}