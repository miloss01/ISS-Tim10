package com.ISSUberTim10.ISSUberTim10.exceptions;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorMessage> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.message), ex.httpStatus);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    protected ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {

        String ret = "";

        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            String fieldName = ((PathImpl) violation.getPropertyPath()).getLeafNode().toString();
            ret += "Field (" + fieldName + ") format is not valid\n";
        }

        return new ResponseEntity<>(ret, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        String ret = "";

        for (ObjectError error : ex.getBindingResult().getAllErrors())
            ret += error.getDefaultMessage() + "\n";

        return new ResponseEntity<>(ret, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { DateTimeParseException.class })
    protected ResponseEntity<String> handleDateTimeParseException(DateTimeParseException ex) {
        System.out.println(ex);
        return new ResponseEntity<>("Wrong date format!", HttpStatus.BAD_REQUEST);
    }

}
