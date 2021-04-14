package com.weatherapi.exception;

public class ResourceUpdateNotAllowedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceUpdateNotAllowedException(String message) {
        super(message);
    }

    public ResourceUpdateNotAllowedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}