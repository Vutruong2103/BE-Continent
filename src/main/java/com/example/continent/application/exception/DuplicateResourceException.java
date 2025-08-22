package com.example.continent.application.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends BaseException {
    public DuplicateResourceException(String code, Object... args) {
        super(HttpStatus.BAD_REQUEST, code, args);
    }
}

