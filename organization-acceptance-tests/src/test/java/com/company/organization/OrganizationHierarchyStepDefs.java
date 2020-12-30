package com.company.organization;

import io.cucumber.java8.En;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.net.http.HttpRequest;

import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpResponse.BodyHandlers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Testcontainers
public class OrganizationHierarchyStepDefs implements En {

    public OrganizationHierarchyStepDefs() {

        Given("we have set the following company hierarchy:", (io.cucumber.datatable.DataTable dataTable) -> {
            assertThat(Infrastructure.applicationContainer.isRunning(), is(true));
            var request = HttpRequest.newBuilder(
                URI.create("http://localhost:" + Infrastructure.applicationPort + "/organization")).GET().build();
            final var response = newHttpClient().send(request, BodyHandlers.ofString());
            assertThat(response.statusCode(), is(200));
            assertThat(response.body(), is("hello"));
        });

        When("^we check the company hierarchy$", () -> {
        });

        Then("^\"([^\"]*)\" is the supervisor of \"([^\"]*)\"$", (String employee, String supervisor) -> {
            assertThat("something", is("broken"));
        });
    }

}
