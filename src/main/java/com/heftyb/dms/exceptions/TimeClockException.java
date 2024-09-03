package com.heftyb.dms.exceptions;

public class TimeClockException extends RuntimeException{
    public TimeClockException(String message) { super(message); }
    public TimeClockException(String message, Throwable cause) { super(message, cause); }
    public TimeClockException(Throwable cause) { super(cause); }
}
