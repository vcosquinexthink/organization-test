package com.company.organization.configuration;

import io.cucumber.java8.En;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class Infrastructure implements En {

    public static final GenericContainer<?> applicationContainer;
    public static final Integer applicationPort;

    static {
        applicationContainer = new GenericContainer<>(
            "docker.io/library/organization-app:latest").withExposedPorts(8080);
        applicationContainer.start();
        applicationPort = applicationContainer.getMappedPort(8080);
    }
}
