package com.example.ics.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ImaggaServiceException extends ResponseStatusException {
    public ImaggaServiceException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public ImaggaServiceException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
