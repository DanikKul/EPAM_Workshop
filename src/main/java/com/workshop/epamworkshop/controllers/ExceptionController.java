package com.workshop.epamworkshop.controllers;

import com.workshop.epamworkshop.errors.NegativeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LogManager.getLogger(ExceptionController.class);

    @ExceptionHandler(value = NegativeException.class)
    public ResponseStatusException negativeException() {
        logger.error("Error: Negative value");
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Negative value error");
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseStatusException parseException() {
        logger.error("Error: Can't parse value");
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Parsing error");
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseStatusException parseAnyException() {
        logger.error("Error: Unexpected error");
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
    }

}
