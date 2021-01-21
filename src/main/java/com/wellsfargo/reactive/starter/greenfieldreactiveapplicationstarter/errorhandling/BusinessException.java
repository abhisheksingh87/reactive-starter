package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.errorhandling;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
