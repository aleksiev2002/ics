package com.example.ics.services.exceptions;

public class ImaggaServiceException extends RuntimeException {
    public ImaggaServiceException(String message, Throwable cause) {
        super(message, cause);
    }
        public ImaggaServiceException(String message) {
            super(message, null);
        }
}
