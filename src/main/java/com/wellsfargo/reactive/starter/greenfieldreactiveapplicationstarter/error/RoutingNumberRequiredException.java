package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RoutingNumberRequiredException extends ResponseStatusException {

    public RoutingNumberRequiredException(HttpStatus status, String message, Throwable e) {
        super(status, message, e);
    }
}
