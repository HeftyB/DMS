package com.heftyb.dms.exceptions;

public class ResourceFoundException extends RuntimeException {
    public ResourceFoundException(String message) {
        super("Error from a BloomTech Application " + message);
    }
}
