package com.company.organization.stepdefs;

import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.net.http.HttpRequest;

import static com.company.organization.configuration.Infrastructure.applicationPort;
import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Testcontainers
@Slf4j
public class SecurityStepDefs implements En {

    private int statusCode;

    public SecurityStepDefs() {

        When("^we call the application with wrong credentials$", () -> {
            final var request = HttpRequest.newBuilder(
                URI.create("http://localhost:" + applicationPort + "/organization"))
                .header("Authorization", "Basic xXxXxXxXxXxXxXxXxX==")
                .GET().build();
            final var response = newHttpClient().send(request, ofString());
            statusCode = response.statusCode();
        });

        Then("^we receive an unauthenticated error code$", () -> {
            assertThat(statusCode, is(401));
        });
    }
}
