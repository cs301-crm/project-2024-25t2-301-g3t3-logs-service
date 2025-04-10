package com.cs301.crm.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleInvalidPageParameters() {
        return new ResponseEntity<>(
                new ErrorResponse(false,"Please enter only numbers for page and limit parameters.",
                        HttpStatus.BAD_REQUEST,
                        ZonedDateTime.now()
                ), HttpStatus.BAD_REQUEST);
    }

}
