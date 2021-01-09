package com.company.organization.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import static com.company.organization.configuration.Infrastructure.applicationPort;
import static java.net.URI.create;
import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.BodyPublishers.ofString;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.util.stream.Collectors.toMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Testcontainers
@Slf4j
public class OrganizationHierarchyStepDefs implements En {

    private String employeesBody;
    private String employeeBody;
    private Map<Object, Object> organizationMap;

    public OrganizationHierarchyStepDefs() {

        Given("we have set the following organization hierarchy:", (final DataTable dataTable) -> {
            final var organizationMap = dataTable.asLists(String.class).stream().skip(1)
                .collect(toMap(row -> row.get(0), row -> row.get(1)));
            final var organizationBodyPublisher = ofString(
                new ObjectMapper().writeValueAsString(organizationMap));
            final var request = newBuilder(
                create("http://localhost:" + applicationPort + "/organization"))
                .header("Content-type", "application/json")
                .header("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
                .POST(organizationBodyPublisher).build();
            assertThat(newHttpClient().send(request, ofString()).statusCode(), is(200));
        });

        When("^we check the organization hierarchy$", () -> {
            final var request = newBuilder(
                create("http://localhost:" + applicationPort + "/organization"))
                .header("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
                .GET().build();
            final var response = newHttpClient().send(request, ofString());
            assertThat(response.statusCode(), is(200));
            employeesBody = response.body();
        });

        Then("^organization hierarchy is:$", (String expected) -> {
            assertThat(employeesBody, is(expected.trim()));
        });

        When("^we try to add the following organization hierarchy:", (final DataTable dataTable) -> {
            organizationMap = dataTable.asLists(String.class).stream().skip(1)
                .collect(toMap(row -> row.get(0), row -> row.get(1)));
        });

        Then("^application rejects it with the following error message \"([^\"]*)\"$", (String errorMessage) -> {
            final var organizationBodyPublisher = ofString(
                new ObjectMapper().writeValueAsString(organizationMap));
            final var request = newBuilder(
                create("http://localhost:" + applicationPort + "/organization"))
                .header("Content-type", "application/json")
                .header("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
                .POST(organizationBodyPublisher).build();
            final var response = newHttpClient().send(request, ofString());
            assertThat(response.statusCode(), is(400));
            final var responseMap = new ObjectMapper().readValue(response.body(), Map.class);
            assertThat(responseMap.get("message"), is(errorMessage));
        });

        When("^we check the management chain for \"([^\"]*)\"$", (String employeeName) -> {
            final var request = newBuilder(
                create("http://localhost:" + applicationPort + "/organization/employee/" + employeeName))
                .header("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
                .GET().build();
            final var response = newHttpClient().send(request, ofString());
            assertThat(response.statusCode(), is(200));
            employeeBody = response.body();
        });

        Then("^management chain is:$", (String expected) -> {
            assertThat(employeeBody, is(expected.trim()));
        });
    }
}
