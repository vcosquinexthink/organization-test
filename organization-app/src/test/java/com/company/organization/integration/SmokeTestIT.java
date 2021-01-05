package com.company.organization.integration;

import com.company.organization.rest.ApplicationController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * This test just guarantees spring boot is properly configured to start.
 * Prevents configuration errors in beans for example to come up before
 * running the more expensive acceptance test.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SmokeTestIT {

    @Autowired
    ApplicationController controller;

    @Test
    public void smokeTest() {
        controller.getOrganization();
    }
}
