package com.company.organization.configuration;

import io.cucumber.java8.En;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class Infrastructure implements En {

    public static final GenericContainer<?> applicationContainer;
    public static final Integer applicationPort;

    static {
        final var tag = System.getProperty("release.version");
        applicationContainer = new GenericContainer<>(
            "docker.io/vcosqui/organization-app:" + tag).withExposedPorts(8080);
        applicationContainer.start();
        applicationPort = applicationContainer.getMappedPort(8080);
    }
}
