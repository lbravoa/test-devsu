package com.devsu.hackerearth.backend.client.handler.exceptions;

import java.lang.RuntimeException;

public class RegisterNotFoundException extends RuntimeException {

    public RegisterNotFoundException() {
        super("Register not found");
    }

}
