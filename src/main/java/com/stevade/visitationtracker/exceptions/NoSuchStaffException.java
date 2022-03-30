package com.stevade.visitationtracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchStaffException extends RuntimeException {
    public NoSuchStaffException(String message) {
        super(message);
    }
}
