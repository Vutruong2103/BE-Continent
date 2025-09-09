package com.example.continent.application.exception;

import org.springframework.http.HttpStatus;

/**
 * Class này dùng khi trùng dữ liệu, trả về 400 Bad Request
 * Khi dữ liệu request gây ra trùng lặp không hợp lệ (validation lỗi, business rule vi phạm).
 */
public class DuplicateResourceException extends BaseException {
    public DuplicateResourceException(String code, Object... args) {
        super(HttpStatus.BAD_REQUEST, code, args);
    }
}

