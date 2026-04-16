package com.fitcontrol.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String message;
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
    private Map<String, String> validationErrors;

    public ErrorResponse() {
    }

    public ErrorResponse(
        String message,
        LocalDateTime timestamp,
        Integer status,
        String error,
        String path,
        Map<String, String> validationErrors
    ) {
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
        this.validationErrors = validationErrors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
