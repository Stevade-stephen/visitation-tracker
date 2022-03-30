package com.stevade.visitationtracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoVisitorFoundException extends RuntimeException {
    public NoVisitorFoundException(String message) {
        super(message);
    }
}
