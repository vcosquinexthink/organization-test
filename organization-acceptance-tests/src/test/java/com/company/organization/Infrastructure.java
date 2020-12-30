package com.company.organization;

import io.cucumber.java8.En;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class Infrastructure implements En {

    public static final GenericContainer<?> applicationContainer;
    public static final Integer applicationPort;

    static {
        applicationContainer = new GenericContainer<>("aikain/simplehttpserver:0.1")
            .withExposedPorts(80);
        applicationContainer.start();
        applicationPort = applicationContainer.getMappedPort(80);
    }
}
