package com.ISSUberTim10.ISSUberTim10.exceptions;

import org.springframework.http.HttpStatus;

public class CustomExceptionWithMessage extends RuntimeException {

    public String message;
    public HttpStatus httpStatus;
    public CustomExceptionWithMessage(String message, HttpStatus httpStatus) {
        super();
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
