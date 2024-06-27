package com.example.demo.domain.exception;

import org.springframework.http.HttpStatus;

public class SkilException extends RuntimeException {

    private HttpStatus statusCode;

    public SkilException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}

