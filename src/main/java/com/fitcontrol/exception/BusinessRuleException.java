package com.fitcontrol.exception;

public class BusinessRuleException extends RuntimeException {

    private final int statusCode;

    public BusinessRuleException(String message) {
        super(message);
        this.statusCode = 409;
    }

    public BusinessRuleException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
