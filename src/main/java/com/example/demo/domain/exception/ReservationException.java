package com.example.demo.domain.exception;

import org.springframework.http.HttpStatus;

public class ReservationException extends RuntimeException {

    private HttpStatus statusCode;

    public ReservationException(HttpStatus statusCode, String message) {
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

