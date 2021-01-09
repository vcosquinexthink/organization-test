package com.company.organization.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class EmployeeTest {

    public static final Employee EMPLOYEE = new Employee("emp-1");
    public static final Employee BOSS = new Employee("boss-1");

    @Test
    public void itShouldBeEqualForSameName() {
        final var employee = new Employee("emp-1");
        assertThat(EMPLOYEE, is(equalTo(employee)));
        employee.setManager(BOSS);
        assertThat(EMPLOYEE, is(equalTo(employee)));
    }

    @Test
    public void itShouldBeSameHashCode() {
        final var employee = new Employee("emp-1");
        assertThat(EMPLOYEE.hashCode(), is(equalTo(employee.hashCode())));
        employee.setManager(BOSS);
        assertThat(EMPLOYEE.hashCode(), is(equalTo(employee.hashCode())));
    }

    @Test
    public void itShouldBeRoot() {
        assertThat(EMPLOYEE.isRoot(), is(true));
    }

    @Test
    public void itShouldNotBeRoot() {
        final var employee = new Employee("emp-1");
        employee.setManager(BOSS);
        assertThat(employee.isRoot(), is(false));
    }
}