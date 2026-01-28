package com.example.lojadevariedades.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final HttpStatus status;
    private final String code;
    private final Object details;

    public CustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.code = null;
        this.details = null;
    }

    public CustomException(HttpStatus status, String message, String code) {
        super(message);
        this.status = status;
        this.code = code;
        this.details = null;
    }

    public CustomException(HttpStatus status, String message, String code, Object details) {
        super(message);
        this.status = status;
        this.code = code;
        this.details = details;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public Object getDetails() {
        return details;
    }
}
