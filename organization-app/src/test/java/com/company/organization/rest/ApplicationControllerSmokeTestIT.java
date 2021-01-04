package com.company.organization.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

/**
 * This test just guarantees spring boot is properly configured to start.
 * Prevents configuration errors in beans for example to come up before
 * running the more expensive acceptance test.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerSmokeTestIT {

    @Autowired
    ApplicationController controller;

    @Test
    public void controllerShouldSetOrganization() {
        controller.setOrganization(
            Map.of("Pete", "Nick",
                "Barbara", "Nick",
                "Nick", "Sophie",
                "Sophie", "Jonas"));
    }
}
