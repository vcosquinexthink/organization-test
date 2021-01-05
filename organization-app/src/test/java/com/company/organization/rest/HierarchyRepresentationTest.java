package com.company.organization.rest;

import com.company.organization.domain.Organization;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class HierarchyRepresentationTest {

    @Test
    @SneakyThrows
    public void itShouldReturnTheRightRepresentation() {
        final var organization = new Organization();
        organization.addEmployees(Map.of(
            "Pete", "Nick",
            "Barbara", "Nick",
            "Nick", "Sophie",
            "Sophie", "Jonas"
        ));
        final var objectMapper = new ObjectMapper();
        final var json = new HierarchyRepresentation(organization, objectMapper).toJson();

        assertThat(json, is("{\"Jonas\":[{\"Sophie\":[{\"Nick\":[{\"Pete\":[]},{\"Barbara\":[]}]}]}]}"));
    }

    @Test
    public void itShouldReturnTheRightRepresentationWhenNoRoot() {

        final var organization = new Organization();
        final var objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        final var json = new HierarchyRepresentation(organization, objectMapper).toJson();

        assertThat(json, is("{}"));
    }
}