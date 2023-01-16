package com.ISSUberTim10.ISSUberTim10.exceptions;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
            ret += "Constraint violation. Field (" + fieldName + ") format is not valid!\n";
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

    @ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
    protected ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        String fieldName = ex.getName();

        return new ResponseEntity<>("Wrong type. Field (" + fieldName + ") format is not valid!\n", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { DateTimeParseException.class })
    protected ResponseEntity<String> handleDateTimeParseException(DateTimeParseException ex) {
        System.out.println(ex);
        return new ResponseEntity<>("Wrong date format!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { UsernameNotFoundException.class })
    protected ResponseEntity<String> handleMethodArgumentNotValidException(UsernameNotFoundException ex) {
        return new ResponseEntity<>("User not found with given username", HttpStatus.BAD_REQUEST);
    }


}
