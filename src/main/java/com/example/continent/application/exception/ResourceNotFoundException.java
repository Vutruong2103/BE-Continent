package com.example.continent.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class này dùng khi không tìm thấy dữ liệu, trả về 404 Not Found
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String code, Object... args) {
        super(HttpStatus.NOT_FOUND, code, args);
    }
}
