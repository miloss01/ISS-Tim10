package com.ISSUberTim10.ISSUberTim10.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorMessage> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.message), ex.httpStatus);
    }

//    @ExceptionHandler(value = { ConstraintViolationException.class })
//    protected ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
//        System.out.println(ex);
//        return new ResponseEntity<>("zabrana", HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(value = { DateTimeParseException.class })
    protected ResponseEntity<String> handleDateTimeParseException(DateTimeParseException ex) {
        System.out.println(ex);
        return new ResponseEntity<>("Wrong date format!", HttpStatus.BAD_REQUEST);
    }

}
