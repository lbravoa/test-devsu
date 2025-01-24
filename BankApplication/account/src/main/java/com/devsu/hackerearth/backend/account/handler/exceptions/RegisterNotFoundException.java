package com.devsu.hackerearth.backend.account.handler.exceptions;

public class RegisterNotFoundException extends RuntimeException {
    
    public RegisterNotFoundException() {
        super("Register not found");
    }

}
