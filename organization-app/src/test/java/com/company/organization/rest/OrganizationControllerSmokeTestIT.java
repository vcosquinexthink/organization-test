package com.company.organization.rest;

import com.company.organization.domain.IllegalOrganizationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

/**
 * This test just guarantees spring boot is properly configured to start.
 * Prevents configuration errors in beans for example to come up before
 * running the more expensive acceptance test.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class OrganizationControllerSmokeTestIT {

    @Autowired
    OrganizationController controller;

    @Test
    @Transactional
    public void controllerShouldSetOrganization() {
        controller.setOrganization(
            Map.of("Pete", "Nick",
                "Barbara", "Nick",
                "Nick", "Sophie",
                "Sophie", "Jonas"));

        final var expected = Map.of("Jonas", List.of(Map.of("Sophie", List.of(Map.of("Nick", List.of(Map.of("Barbara", List.of()), Map.of("Pete", List.of())))))));
        assertThat(controller.getOrganization(),
            is(expected));
    }

    @Test
    @Transactional
    public void controllerShouldUpdateOrganization() {
        controller.setOrganization(
            Map.of("Pete", "Nick",
                "Barbara", "Nick",
                "Nick", "Sophie",
                "Sophie", "Jonas"));

        final var expectedBefore = Map.of("Jonas", List.of(Map.of("Sophie", List.of(Map.of("Nick", List.of(Map.of("Barbara", List.of()), Map.of("Pete", List.of())))))));
        assertThat(controller.getOrganization(), is(expectedBefore));

        controller.setOrganization(Map.of(
            "Angela", "Jonas"));
        final var expectedAfter = Map.of("Jonas", List.of(Map.of("Angela", List.of()), Map.of("Sophie", List.of(Map.of("Nick", List.of(Map.of("Barbara", List.of()), Map.of("Pete", List.of())))))));
        assertThat(controller.getOrganization(), is(expectedAfter));
    }

    @Test
    @Transactional
    public void controllerShouldPreventCyclicDependencies() {
        controller.setOrganization(
            Map.of("Pete", "Nick",
                "Barbara", "Nick",
                "Nick", "Sophie",
                "Sophie", "Jonas"));
        final var exception = assertThrows(IllegalOrganizationException.class, () -> {
            controller.setOrganization(Map.of("Sophie", "Nick"));
        });
        assertThat(exception.getMessage(), is("Error: There is a cyclic dependency in employee [Nick]"));
    }
}
