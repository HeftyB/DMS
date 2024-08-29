package com.heftyb.dms.exceptions;

public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1867326263418521L;

    public DataNotFoundException(String message) {
        super(String.format("Error: %s", message));
    }
}
