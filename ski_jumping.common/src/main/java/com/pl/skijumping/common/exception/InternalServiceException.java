package com.pl.skijumping.common.exception;

public class InternalServiceException extends Exception {
    public InternalServiceException(String errorMessage) {super(errorMessage);}
    public InternalServiceException(String errorMessage, Exception exception) {super(errorMessage, exception);}
}
