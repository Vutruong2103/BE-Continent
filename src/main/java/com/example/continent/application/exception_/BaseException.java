package com.example.continent.application.exception_;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

/**
 * BaseException là cha của tất cả exception tuỳ chỉnh.
 * Nó tự động gắn sẵn ProblemDetail (status, title, detail).
 */
public abstract class BaseException extends RuntimeException {

    private final ProblemDetail problemDetail;

    protected BaseException(HttpStatus status, String code, Object... args) {
        super(code);
        this.problemDetail = ProblemDetail.forStatus(status);
        this.problemDetail.setTitle(status.getReasonPhrase());
        this.problemDetail.setDetail(code);
    }

    public ProblemDetail getProblemDetail() {
        return problemDetail;
    }
}
