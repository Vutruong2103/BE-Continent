package com.example.continent.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
//request sai dữ liệu, không hợp lệ, trả về 400 Bad Request
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}