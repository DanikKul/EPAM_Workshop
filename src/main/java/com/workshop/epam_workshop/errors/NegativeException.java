package com.workshop.epam_workshop.errors;


public class NegativeException extends RuntimeException{
    public NegativeException(String message) {
        super(message);
    }
}
