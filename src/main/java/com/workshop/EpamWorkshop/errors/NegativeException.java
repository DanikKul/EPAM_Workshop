package com.workshop.EpamWorkshop.errors;


public class NegativeException extends RuntimeException{
    public NegativeException(String message) {
        super(message);
    }
}
