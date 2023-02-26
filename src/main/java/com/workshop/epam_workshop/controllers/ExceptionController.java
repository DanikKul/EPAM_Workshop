package com.workshop.epam_workshop.controllers;

import com.workshop.epam_workshop.errors.NegativeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = NegativeException.class)
    public ResponseStatusException negativeException() {
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Negative value error");
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseStatusException parseException() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parsing error");
    }

}
