package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.error;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
