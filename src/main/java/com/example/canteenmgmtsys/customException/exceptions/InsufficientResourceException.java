package com.example.canteenmgmtsys.customException.exceptions;

public class InsufficientResourceException extends RuntimeException{
    public InsufficientResourceException(String message) {
        super(message);
    }
}
