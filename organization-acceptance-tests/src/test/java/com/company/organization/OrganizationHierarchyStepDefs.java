package com.company.organization;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Map;
import java.util.stream.Collectors;

import static com.company.organization.Infrastructure.applicationPort;
import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.BodyPublishers.ofString;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Testcontainers
@Slf4j
public class OrganizationHierarchyStepDefs implements En {

    private String organization;

    public OrganizationHierarchyStepDefs() {

        Given("we have set the following organization hierarchy:", (final DataTable dataTable) -> {
            final var organizationMap = dataTable.asLists(String.class).stream().skip(1)
                .collect(Collectors.toMap(row -> row.get(0), row -> row.get(1)));
            final var organizationBodyPublisher = ofString(
                new ObjectMapper().writeValueAsString(organizationMap));
            final var request = HttpRequest.newBuilder(
                URI.create("http://localhost:" + applicationPort + "/organization"))
                .header("Content-type", "application/json")
                .POST(organizationBodyPublisher).build();
            final var response = newHttpClient().send(request, ofString());
            assertThat(response.statusCode(), is(200));
        });

        When("^we check the organization hierarchy$", () -> {
            final var request = HttpRequest.newBuilder(
                URI.create("http://localhost:" + applicationPort + "/organization")).GET().build();
            final var response = newHttpClient().send(request, ofString());
            assertThat(response.statusCode(), is(200));
            organization = response.body();
        });

        Then("^\"([^\"]*)\" is supervised by \"([^\"]*)\"$",
            (String employee, String supervisor) ->
                assertThat(new ObjectMapper().readValue(organization, Map.class).get(employee), is(supervisor)));
    }
}
