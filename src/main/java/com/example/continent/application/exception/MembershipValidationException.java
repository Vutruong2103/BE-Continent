package com.example.continent.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MembershipValidationException extends RuntimeException {
    public MembershipValidationException(String message) {
        super(message);
    }
}
