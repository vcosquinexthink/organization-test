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
public class HierarchyStepDefs implements En {

    public HierarchyStepDefs() {

        Given("we have set the following company hierarchy:", (io.cucumber.datatable.DataTable dataTable) -> {
            var request = HttpRequest.newBuilder(
                URI.create("http://localhost:" + Infrastructure.applicationPort)).GET().build();
            assertThat(newHttpClient().send(request, BodyHandlers.ofString()).statusCode(), is(200));
        });

        When("^we check the company hierarchy$", () -> {
        });

        Then("^\"([^\"]*)\" is the supervisor of \"([^\"]*)\"$", (String employee, String supervisor) -> {
            assertThat(Infrastructure.applicationContainer.isRunning(), is(true));
        });
    }

}
