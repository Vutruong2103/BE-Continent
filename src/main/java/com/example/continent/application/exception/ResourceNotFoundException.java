package com.example.continent.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//dùng khi không tìm thấy dữ liệu, trả về 404 Not Found
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String code, Object... args) {
        super(HttpStatus.NOT_FOUND, code, args);
    }
}
