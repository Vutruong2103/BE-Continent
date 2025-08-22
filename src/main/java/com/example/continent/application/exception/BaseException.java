package com.example.continent.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public abstract class BaseException extends RuntimeException {
    private final ProblemDetail problemDetail;

    protected BaseException(HttpStatus status, String code, Object... args) {
        super(code); // giữ lại code để tra trong i18n
        this.problemDetail = ProblemDetail.forStatus(status);
        this.problemDetail.setTitle(status.getReasonPhrase());
        this.problemDetail.setDetail(code); // sẽ override trong GlobalExceptionHandler
    }

    public ProblemDetail getProblemDetail() {
        return problemDetail;
    }
}
