package com.ishanitech.ipalikawebapp.exception;

import org.springframework.http.HttpStatus;

abstract class CustomBaseException extends RuntimeException {
    private String message;
    public CustomBaseException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    protected abstract HttpStatus getStatus();
}
