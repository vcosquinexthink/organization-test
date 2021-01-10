package com.company.organization.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IllegalOrganizationException extends RuntimeException {

    public IllegalOrganizationException(final String message) {
        super(message);
    }
}
