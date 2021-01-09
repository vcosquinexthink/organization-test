package com.company.organization.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CyclicDependencyException extends RuntimeException {

    public CyclicDependencyException(final String message) {
        super(message);
    }
}
