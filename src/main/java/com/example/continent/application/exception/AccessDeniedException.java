package com.example.continent.application.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends BaseException {
    public AccessDeniedException(String code, Object... args) {
        super(HttpStatus.FORBIDDEN, code, args);
    }
}