package com.example.continent.application.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    public BadRequestException(String code, Object... args) {
        super(HttpStatus.BAD_REQUEST, code, args);
    }
}