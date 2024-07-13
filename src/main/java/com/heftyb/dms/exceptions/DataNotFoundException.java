package com.heftyb.dms.exceptions;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(String.format("Error: %s", message));
    }
}
