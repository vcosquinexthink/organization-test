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
public class ManagementStepDefs implements En {

    private int statusCode;
    private String metricsReport;

    public ManagementStepDefs() {

        When("^we call the application management metrics endpoints$", () -> {
            final var request = HttpRequest.newBuilder(
                URI.create("http://localhost:" + applicationPort + "/actuator/prometheus"))
                .GET().build();
            final var httpResponse = newHttpClient().send(request, ofString());
            statusCode = httpResponse.statusCode();
            metricsReport = httpResponse.body();
        });

        Then("^we receive a valid metrics report$", () -> {
            assertThat(statusCode, is(200));
            assertThat(metricsReport.contains("logback_events_total"), is(true));
        });
    }
}
