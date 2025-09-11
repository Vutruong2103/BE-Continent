package com.example.continent.application.exception_;

import org.springframework.http.HttpStatus;

/**
 * Class này nó đại diện cho lỗi tài nguyên đã tồn tại
 * cố định HTTP status = 409 Conflict.
 * Khi tài nguyên đã tồn tại trên hệ thống (thường khi insert một bản ghi mới nhưng đã có rồi).
 */
public class ResourceAlreadyExistsException extends BaseException {
    public ResourceAlreadyExistsException(String code, Object... args) {
        super(HttpStatus.CONFLICT, code, args);
    }
}